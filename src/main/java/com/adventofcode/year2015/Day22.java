package com.adventofcode.year2015;

import com.adventofcode.graph.AStar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day22 {
    private static final TerminalState WINNING_STATE = TerminalState.of(true);
    private static final TerminalState LOOSING_STATE = TerminalState.of(false);
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22.class);

    public static List<AStar.Move<State>> nextStatePartOne(State state) {
        return nextState(state, false);
    }

    public static List<AStar.Move<State>> nextStatePartTwo(State state) {
        return nextState(state, true);
    }

    public static List<AStar.Move<State>> nextState(State state, boolean hardMode) {
        if (state instanceof TerminalState) {
            return Collections.emptyList();
        } else if (state instanceof PlayableState playableState) {
            Player player = playableState.player();
            Boss boss = playableState.boss();

            if (playableState.turn()) {
                // Player's turn
                if (hardMode) {
                    player = Player.of(player.hitPoints() - 1, player.mana(), player.effects());
                    if (player.hitPoints() <= 0) {
                        return List.of(AStar.Move.of(LOOSING_STATE, Integer.MAX_VALUE));
                    }
                }

                List<AStar.Move<State>> nextState = new ArrayList<>();
                Map<Spells, Integer> currentEffects = new EnumMap<>(Spells.class);
                for (Map.Entry<Spells, Integer> entry : player.effects().entrySet()) {
                    if (entry.getValue() > 1) {
                        currentEffects.put(entry.getKey(), entry.getValue() - 1);
                    }

                    switch (entry.getKey()) {
                        case Poison -> boss = Boss.of(boss.hitPoints() - 3, boss.damage());
                        case Recharge -> player = Player.of(player.hitPoints(), player.mana() + 101, player.effects());
                    }
                }

                if (boss.hitPoints() <= 0) {
                    return List.of(AStar.Move.of(WINNING_STATE, 0));
                }

                for (Spells spell : Spells.values()) {
                    if (spell.getCost() > player.mana()) {
                        continue;
                    }
                    Map<Spells, Integer> newEffects = new EnumMap<>(currentEffects);
                    newEffects.put(spell, spell.getDuration());
                    Player newPlayer = switch (spell) {
                        case Drain -> Player.of(player.hitPoints() + 2, player.mana() - spell.getCost(), newEffects);
                        case MagicMissile, Shield, Recharge, Poison -> Player.of(player.hitPoints(), player.mana() - spell.getCost(), newEffects);
                    };
                    Boss newBoss = switch (spell) {
                        case MagicMissile -> Boss.of(boss.hitPoints() - 4, boss.damage());
                        case Drain -> Boss.of(boss.hitPoints() - 2, boss.damage());
                        case Shield, Recharge, Poison -> Boss.of(boss.hitPoints(), boss.damage());
                    };
                    if (newBoss.hitPoints() <= 0) {
                        nextState.add(AStar.Move.of(WINNING_STATE, spell.getCost()));
                    } else {
                        nextState.add(AStar.Move.of(PlayableState.of(
                                newPlayer, newBoss, false
                        ), spell.getCost()));
                    }
                }

                return nextState;
            } else {
                // Boss's turn
                int armor = 0;
                Map<Spells, Integer> currentEffects = new EnumMap<>(Spells.class);
                for (Map.Entry<Spells, Integer> entry : player.effects().entrySet()) {
                    if (entry.getValue() > 1) {
                        currentEffects.put(entry.getKey(), entry.getValue() - 1);
                    }

                    switch (entry.getKey()) {
                        case Shield -> armor += 7;
                        case Poison -> boss = Boss.of(boss.hitPoints() - 3, boss.damage());
                        case Recharge -> player = Player.of(player.hitPoints(), player.mana() + 101, player.effects());
                    }
                }

                player = Player.of(player.hitPoints(), player.mana(), currentEffects);
                if (boss.hitPoints() <= 0) {
                    return List.of(AStar.Move.of(WINNING_STATE, 0));
                }

                int damage = boss.damage() - armor;
                if (damage <= 0) {
                    damage = 1;
                }

                player = Player.of(player.hitPoints() - damage, player.mana(), player.effects());
                if (player.hitPoints() <= 0) {
                    return List.of(AStar.Move.of(LOOSING_STATE, Integer.MAX_VALUE));
                }

                return List.of(AStar.Move.of(PlayableState.of(player, boss, true), 0));
            }
        } else {
            throw new IllegalStateException("Unknown type of state: " + state.getClass());
        }
    }

    public static Boss readBossStats(Scanner scanner) {
        Map<String, Integer> bossStats = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(": ");
            bossStats.put(split[0], Integer.valueOf(split[1]));
        }

        Boss boss = Boss.of(bossStats.get("Hit Points"), bossStats.get("Damage"));
        LOGGER.info("bossStats : {}, boss: {}", bossStats, boss);
        return boss;
    }

    /**
     * --- Day 22: Wizard Simulator 20XX ---
     *
     * Little Henry Case decides that defeating bosses with swords and stuff is
     * boring. Now he's playing the game with a wizard. Of course, he gets stuck
     * on another boss and needs your help again.
     *
     * In this version, combat still proceeds with the player and the boss taking
     * alternating turns. The player still goes first. Now, however, you don't get
     * any equipment; instead, you must choose one of your spells to cast. The
     * first character at or below 0 hit points loses.
     *
     * Since you're a wizard, you don't get to wear armor, and you can't attack
     * normally. However, since you do magic damage, your opponent's armor is
     * ignored, and so the boss effectively has zero armor as well. As before, if
     * armor (from a spell, in this case) would reduce damage below 1, it becomes
     * 1 instead - that is, the boss' attacks always deal at least 1 damage.
     *
     * On each of your turns, you must select one of your spells to cast. If you
     * cannot afford to cast any spell, you lose. Spells cost mana; you start with
     * 500 mana, but have no maximum limit. You must have enough mana to cast a
     * spell, and its cost is immediately deducted when you cast it. Your spells
     * are Magic Missile, Drain, Shield, Poison, and Recharge.
     *
     *   - Magic Missile costs 53 mana. It instantly does 4 damage.
     *   - Drain costs 73 mana. It instantly does 2 damage and heals you for 2
     *     hit points.
     *   - Shield costs 113 mana. It starts an effect that lasts for 6 turns.
     *     While it is active, your armor is increased by 7.
     *   - Poison costs 173 mana. It starts an effect that lasts for 6 turns. At
     *     the start of each turn while it is active, it deals the boss 3 damage.
     *   - Recharge costs 229 mana. It starts an effect that lasts for 5 turns.
     *     At the start of each turn while it is active, it gives you 101 new
     *     mana.
     *
     * Effects all work the same way. Effects apply at the start of both the
     * player's turns and the boss' turns. Effects are created with a timer (the
     * number of turns they last); at the start of each turn, after they apply any
     * effect they have, their timer is decreased by one. If this decreases the
     * timer to zero, the effect ends. You cannot cast a spell that would start an
     * effect which is already active. However, effects can be started on the same
     * turn they end.
     *
     * For example, suppose the player has 10 hit points and 250 mana, and that
     * the boss has 13 hit points and 8 damage:
     *
     * -- Player turn --
     * - Player has 10 hit points, 0 armor, 250 mana
     * - Boss has 13 hit points
     * Player casts Poison.
     *
     * -- Boss turn --
     * - Player has 10 hit points, 0 armor, 77 mana
     * - Boss has 13 hit points
     * Poison deals 3 damage; its timer is now 5.
     * Boss attacks for 8 damage.
     *
     * -- Player turn --
     * - Player has 2 hit points, 0 armor, 77 mana
     * - Boss has 10 hit points
     * Poison deals 3 damage; its timer is now 4.
     * Player casts Magic Missile, dealing 4 damage.
     *
     * -- Boss turn --
     * - Player has 2 hit points, 0 armor, 24 mana
     * - Boss has 3 hit points
     * Poison deals 3 damage. This kills the boss, and the player wins.
     * Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
     *
     * -- Player turn --
     * - Player has 10 hit points, 0 armor, 250 mana
     * - Boss has 14 hit points
     * Player casts Recharge.
     *
     * -- Boss turn --
     * - Player has 10 hit points, 0 armor, 21 mana
     * - Boss has 14 hit points
     * Recharge provides 101 mana; its timer is now 4.
     * Boss attacks for 8 damage!
     *
     * -- Player turn --
     * - Player has 2 hit points, 0 armor, 122 mana
     * - Boss has 14 hit points
     * Recharge provides 101 mana; its timer is now 3.
     * Player casts Shield, increasing armor by 7.
     *
     * -- Boss turn --
     * - Player has 2 hit points, 7 armor, 110 mana
     * - Boss has 14 hit points
     * Shield's timer is now 5.
     * Recharge provides 101 mana; its timer is now 2.
     * Boss attacks for 8 - 7 = 1 damage!
     *
     * -- Player turn --
     * - Player has 1 hit point, 7 armor, 211 mana
     * - Boss has 14 hit points
     * Shield's timer is now 4.
     * Recharge provides 101 mana; its timer is now 1.
     * Player casts Drain, dealing 2 damage, and healing 2 hit points.
     *
     * -- Boss turn --
     * - Player has 3 hit points, 7 armor, 239 mana
     * - Boss has 12 hit points
     * Shield's timer is now 3.
     * Recharge provides 101 mana; its timer is now 0.
     * Recharge wears off.
     * Boss attacks for 8 - 7 = 1 damage!
     *
     * -- Player turn --
     * - Player has 2 hit points, 7 armor, 340 mana
     * - Boss has 12 hit points
     * Shield's timer is now 2.
     * Player casts Poison.
     *
     * -- Boss turn --
     * - Player has 2 hit points, 7 armor, 167 mana
     * - Boss has 12 hit points
     * Shield's timer is now 1.
     * Poison deals 3 damage; its timer is now 5.
     * Boss attacks for 8 - 7 = 1 damage!
     *
     * -- Player turn --
     * - Player has 1 hit point, 7 armor, 167 mana
     * - Boss has 9 hit points
     * Shield's timer is now 0.
     * Shield wears off, decreasing armor by 7.
     * Poison deals 3 damage; its timer is now 4.
     * Player casts Magic Missile, dealing 4 damage.
     *
     * -- Boss turn --
     * - Player has 1 hit point, 0 armor, 114 mana
     * - Boss has 2 hit points
     * Poison deals 3 damage. This kills the boss, and the player wins.
     *
     * You start with 50 hit points and 500 mana points. The boss's actual stats
     * are in your puzzle input. What is the least amount of mana you can spend
     * and still win the fight? (Do not include mana recharge effects as
     * "spending" negative mana.)
     *
     * Your puzzle answer was 953.
     */
    public static long gamePartOne(Scanner scanner) {
        Boss boss = readBossStats(scanner);
        Player player = Player.of(50, 500, new EnumMap<>(Spells.class));

        return gamePartOne(player, boss);
    }

    public static long gamePartOne(Player player, Boss boss) {
        return AStar.algorithm(Day22::nextStatePartOne, PlayableState.of(player, boss, true), WINNING_STATE);
    }

    /**
     * --- Part Two ---
     * On the next run through the game, you increase the difficulty to hard.
     *
     * At the start of each player turn (before any other effects apply), you lose
     * 1 hit point. If this brings you to or below 0 hit points, you lose.
     *
     * With the same starting stats for you and the boss, what is the least amount
     * of mana you can spend and still win the fight?
     *
     * Your puzzle answer was 1289.
     */
    public static long gamePartTwo(Scanner scanner) {
        Boss boss = readBossStats(scanner);
        Player player = Player.of(50, 500, new EnumMap<>(Spells.class));

        return gamePartTwo(player, boss);
    }

    public static long gamePartTwo(Player player, Boss boss) {
        return AStar.algorithm(Day22::nextStatePartTwo, PlayableState.of(player, boss, true), WINNING_STATE);
    }


    public enum Spells {
        MagicMissile(53, 1), // costs 53 mana. It instantly does 4 damage.
        Drain(73, 1), // costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
        Shield(113, 6), // costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
        Poison(173, 6), // costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
        Recharge(229, 5); // costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.

        private final int cost;
        private final int duration;

        Spells(int cost, int duration) {
            this.cost = cost;
            this.duration = duration;
        }

        public int getCost() {
            return cost;
        }

        public int getDuration() {
            return duration;
        }
    }

    public interface State {

    }

    public record PlayableState(Player player, Boss boss, boolean turn) implements State {
        public static PlayableState of(Player player, Boss boss, boolean turn) {
            return new PlayableState(player, boss, turn);
        }
    }

    public record TerminalState(boolean winner) implements State {
        public static TerminalState of(boolean winner) {
            return new TerminalState(winner);
        }
    }

    public record Player(int hitPoints, int mana, Map<Spells, Integer> effects) {
        public static Player of(int hitPoints, int mana, Map<Spells, Integer> effects) {
            return new Player(hitPoints, mana, effects);
        }
    }

    public record Boss(int hitPoints, int damage) {
        public static Boss of(int hitPoints, int damage) {
            return new Boss(hitPoints, damage);
        }
    }
}
