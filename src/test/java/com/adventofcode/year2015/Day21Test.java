package com.adventofcode.year2015;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private final Map<String, List<Item>> items;

    Day21Test() {
        String shopInput = """
                Weapons:    Cost  Damage  Armor
                Dagger        8     4       0
                Shortsword   10     5       0
                Warhammer    25     6       0
                Longsword    40     7       0
                Greataxe     74     8       0
                                
                Armor:      Cost  Damage  Armor
                Leather      13     0       1
                Chainmail    31     0       2
                Splintmail   53     0       3
                Bandedmail   75     0       4
                Platemail   102     0       5
                                
                Rings:      Cost  Damage  Armor
                Damage +1    25     1       0
                Damage +2    50     2       0
                Damage +3   100     3       0
                Defense +1   20     0       1
                Defense +2   40     0       2
                Defense +3   80     0       3
                                
                """;

        Scanner scanner = new Scanner(shopInput);

        items = new HashMap<>();
        String currentType = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }

            int indexOf = line.indexOf(':');
            if (indexOf != -1) {
                currentType = line.substring(0, indexOf);
                LOGGER.debug("Type: {}", currentType);
            } else {
                String name = line.substring(0, 12).trim();
                int[] stats = Arrays.stream(line.substring(12).split(" ")).filter(NumberUtils::isParsable).mapToInt(Integer::parseInt).toArray();
                Item item = Item.of(name, stats[0], stats[1], stats[2]);
                LOGGER.debug("Object: {}", item);
                items.computeIfAbsent(currentType, ignore -> new ArrayList<>()).add(item);
            }
        }

        LOGGER.debug("Items: {}", items);
    }

    private static Player createPlayer(int hitPoints, List<Item> items) {
        int armor = items.stream().mapToInt(Item::armor).sum();
        int damage = items.stream().mapToInt(Item::damage).sum();
        return Player.of("player", hitPoints, damage, armor);
    }

    private static boolean fight(Player player, Player boss) {
        while (true) {
            boss = player.attack(boss);
            if (boss.hitPoints() <= 0) {
                LOGGER.debug("Player wins!");
                return true;
            }

            player = boss.attack(player);
            if (player.hitPoints() <= 0) {
                LOGGER.debug("Boss wins!");
                return false;
            }
        }
    }

    private static Player readBossStats(Scanner scanner) {
        Map<String, Integer> bossStats = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(": ");
            bossStats.put(split[0], Integer.valueOf(split[1]));
        }

        Player boss = Player.of("boss", bossStats.get("Hit Points"), bossStats.get("Damage"), bossStats.get("Armor"));
        LOGGER.info("bossStats : {}, boss: {}", bossStats, boss);
        return boss;
    }

    private static void loosingStrategy(AtomicInteger worstCost, Boolean win, List<Item> items) {
        if (!win) {
            int cost = items.stream().mapToInt(Item::cost).sum();
            if (worstCost.get() < cost) {
                worstCost.set(cost);
            }
        }
    }

    private static void winningStrategy(AtomicInteger bestCost, Boolean win, List<Item> items) {
        if (win) {
            int cost = items.stream().mapToInt(Item::cost).sum();
            if (cost < bestCost.get()) {
                bestCost.set(cost);
            }
        }
    }

    private void runFights(Scanner scanner, BiConsumer<Boolean, List<Item>> consumer) {
        Player boss = readBossStats(scanner);

        List<Item> weapons = items.get("Weapons");
        List<Item> armors = items.get("Armor");
        List<Item> rings = items.get("Rings");

        for (Item myWeapon : weapons) {
            int size = 1 << rings.size();
            for (long r = 0; r < size; ++r) {
                BitSet bitSet = BitSet.valueOf(new long[]{r});
                if (bitSet.cardinality() < 3) {
                    List<Item> myRings = bitSet.stream().mapToObj(rings::get).toList();
                    {
                        List<Item> myItems = new ArrayList<>(myRings);
                        myItems.add(myWeapon);

                        Player player = createPlayer(100, myItems);
                        consumer.accept(fight(player, boss), myItems);
                    }

                    for (Item armor : armors) {
                        List<Item> myItems = new ArrayList<>(myRings);
                        myItems.add(myWeapon);
                        myItems.add(armor);

                        Player player = createPlayer(100, myItems);
                        consumer.accept(fight(player, boss), myItems);
                    }
                }
            }
        }
    }

    @Test
    void inputExample() {
        Player player = Player.of("player", 8, 5, 5);
        Player boss = Player.of("boss", 12, 7, 2);

        assertThat(fight(player, boss)).isTrue();
    }

    /**
     * --- Day 21: RPG Simulator 20XX ---
     *
     * Little Henry Case got a new video game for Christmas. It's an RPG, and he's
     * stuck on a boss. He needs to know what equipment to buy at the shop. He
     * hands you the controller.
     *
     * In this game, the player (you) and the enemy (the boss) take turns
     * attacking. The player always goes first. Each attack reduces the opponent's
     * hit points by at least 1. The first character at or below 0 hit points
     * loses.
     *
     * Damage dealt by an attacker each turn is equal to the attacker's damage
     * score minus the defender's armor score. An attacker always does at least 1
     * damage. So, if the attacker has a damage score of 8, and the defender has
     * an armor score of 3, the defender loses 5 hit points. If the defender had
     * an armor score of 300, the defender would still lose 1 hit point.
     *
     * Your damage score and armor score both start at zero. They can be increased
     * by buying items in exchange for gold. You start with no items and have as
     * much gold as you need. Your total damage or armor is equal to the sum of
     * those stats from all of your items. You have 100 hit points.
     *
     * Here is what the item shop is selling:
     *
     * Weapons:    Cost  Damage  Armor
     * Dagger        8     4       0
     * Shortsword   10     5       0
     * Warhammer    25     6       0
     * Longsword    40     7       0
     * Greataxe     74     8       0
     *
     * Armor:      Cost  Damage  Armor
     * Leather      13     0       1
     * Chainmail    31     0       2
     * Splintmail   53     0       3
     * Bandedmail   75     0       4
     * Platemail   102     0       5
     *
     * Rings:      Cost  Damage  Armor
     * Damage +1    25     1       0
     * Damage +2    50     2       0
     * Damage +3   100     3       0
     * Defense +1   20     0       1
     * Defense +2   40     0       2
     * Defense +3   80     0       3
     *
     * You must buy exactly one weapon; no dual-wielding. Armor is optional, but
     * you can't use more than one. You can buy 0-2 rings (at most one for each
     * hand). You must use any items you buy. The shop only has one of each item,
     * so you can't buy, for example, two rings of Damage +3.
     *
     * For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that
     * the boss has 12 hit points, 7 damage, and 2 armor:
     *
     *   - The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
     *   - The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
     *   - The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
     *   - The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
     *   - The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
     *   - The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
     *   - The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
     *
     * In this scenario, the player wins! (Barely.)
     *
     * You have 100 hit points. The boss's actual stats are in your puzzle input.
     * What is the least amount of gold you can spend and still win the fight?
     *
     * Your puzzle answer was 111.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2015/day/21/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            AtomicInteger bestCost = new AtomicInteger(Integer.MAX_VALUE);
            runFights(scanner, (win, items) -> winningStrategy(bestCost, win, items));
            assertThat(bestCost).hasValue(111);
        }
    }

    /**
     * --- Part Two ---
     *
     * Turns out the shopkeeper is working with the boss, and can persuade you to
     * buy whatever items he wants. The other rules still apply, and he still only
     * has one of each item.
     *
     * What is the most amount of gold you can spend and still lose the fight?
     *
     * Your puzzle answer was 188.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2015/day/21/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            AtomicInteger worstCost = new AtomicInteger(Integer.MIN_VALUE);
            runFights(scanner, (win, items) -> loosingStrategy(worstCost, win, items));
            assertThat(worstCost).hasValue(188);
        }
    }

    record Player(String name, int hitPoints, int damage, int armor) {
        public static Player of(String name, int hitPoints, int damage, int armor) {
            return new Player(name, hitPoints, damage, armor);
        }

        public Player attack(Player p) {
            int damage = this.damage - p.armor;
            if (damage <= 0) {
                damage = 1;
            }
            int hitPoints = p.hitPoints - damage;
            if (hitPoints < 0) {
                hitPoints = 0;
            }
            LOGGER.trace("The {} deals {} damage; the {} goes down to {} hit points.", name, damage, p.name, hitPoints);
            return new Player(p.name, hitPoints, p.damage, p.armor);
        }
    }

    record Item(String name, int cost, int damage, int armor) {
        public static Item of(String name, int cost, int damage, int armor) {
            return new Item(name, cost, damage, armor);
        }
    }
}
