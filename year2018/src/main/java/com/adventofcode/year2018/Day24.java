package com.adventofcode.year2018;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.Pair;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day24 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);
    private static final Pattern PATTERN = Pattern.compile("(\\d+) units each with (\\d+) hit points(?: \\((.*)\\))? with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)");

    private Day24() {
        // No-Op
    }

    private static List<Unit> readUnits(Scanner scanner) {
        boolean readInfection = false;

        int id = 0;

        List<Unit> units = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("Infection:".equals(line)) {
                readInfection = true;
                id = 0;
                continue;
            }

            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int groupCount = matcher.groupCount();
                int n = Integer.parseInt(matcher.group(1));
                int hp = Integer.parseInt(matcher.group(2));
                String attribute = null;
                int damage;
                String attack;
                int initiative;
                if (groupCount == 6) {
                    attribute = matcher.group(3);
                    damage = Integer.parseInt(matcher.group(4));
                    attack = matcher.group(5);
                    initiative = Integer.parseInt(matcher.group(6));
                } else {
                    damage = Integer.parseInt(matcher.group(3));
                    attack = matcher.group(4);
                    initiative = Integer.parseInt(matcher.group(5));
                }

                List<String> weakness = new ArrayList<>();
                List<String> immunities = new ArrayList<>();
                if (attribute != null) {
                    for (String s : Splitter.on("; ").split(attribute)) {
                        String trim = s.trim();
                        if (trim.startsWith("weak to ")) {
                            weakness.addAll(Arrays.asList(trim.substring("weak to ".length()).split(", ")));
                        } else if (trim.startsWith("immune to ")) {
                            immunities.addAll(Arrays.asList(trim.substring("immune to ".length()).split(", ")));
                        }
                    }
                }

                LOGGER.info("n = {}; hp = {}; attribute = '{}'; damage = {}; attack = {}; initiative = {}; weakness = {}, immunities = {}", n, hp, attribute, damage, attack, initiative, weakness, immunities);
                if (readInfection) {
                    String name = "Infection_" + ++id;
                    Unit unit = new Unit(name, n, hp, Set.copyOf(immunities), Set.copyOf(weakness), initiative, attack, damage, Side.INFECTION);

                    units.add(unit);
                } else {
                    String name = "System_" + ++id;
                    Unit unit = new Unit(name, n, hp, Set.copyOf(immunities), Set.copyOf(weakness), initiative, attack, damage, Side.IMMUNE_SYSTEM);

                    units.add(unit);
                }
            }
        }

        LOGGER.info("units = {}", units);
        return units;
    }

    @SuppressWarnings("NullAway")
    private static Pair<Side, Integer> battle(List<Unit> originalUnits, int boost) {
        List<Unit> units = new ArrayList<>();
        for (Unit unit : originalUnits) {
            int newDamage = unit.dmg + (unit.side == Side.IMMUNE_SYSTEM ? boost : 0);
            units.add(unit.withDamage(newDamage));
        }

        Map<Side, Integer> count = new EnumMap<>(Side.class);

        while (true) {
            units.sort(Unit::comparePower);
            for (Unit unit : units) {
                if (unit.n.intValue() <= 0) {
                    throw new IllegalStateException();
                }
            }

            LOGGER.trace("units: {}", units);

            Set<String> chosen = new HashSet<>();
            for (Unit u : units) {
                Optional<Attack> target = units.stream()
                        .filter(v -> v.side != u.side)
                        .filter(v -> !chosen.contains(v.id))
                        .map(v -> new Attack(v, u.damageTo(v), v.power(), v.init))
                        .filter(v -> v.dmg != 0)
                        .min(Attack::compareAttack);

                target.ifPresent(a -> {
                    if (chosen.add(a.target.id)) {
                        u.target.set(a.target);
                        LOGGER.trace("{}'' will attack '{}'", u.id, a.target.id);
                    }
                });
            }

            units.sort(Comparator.comparingInt(Unit::init).reversed());

            boolean anyKilled = false;

            for (Unit u : units) {
                Unit target = u.target.get();
                if (target != null) {
                    LOGGER.trace("{}'' attacks '{}'", u.id, target.id);
                    int dmg = u.damageTo(target);
                    int killed = Math.min(target.n.intValue(), dmg / target.hp);
                    if (killed > 0) {
                        anyKilled = true;
                    }
                    target.n.add(-killed);
                }
            }

            units = units.stream()
                    .filter(u -> u.n.intValue() > 0)
                    .collect(Collectors.toCollection(ArrayList::new));
            units.forEach(u -> u.target.set(null));

            if (!anyKilled) {
                return Pair.of(Side.INFECTION, count.get(Side.INFECTION));
            }

            for (Side side : Side.values()) {
                count.put(side, 0);
            }

            for (Unit unit : units) {
                count.merge(unit.side, unit.n.intValue(), Integer::sum);
            }

            if (count.get(Side.INFECTION) == 0) {
                return Pair.of(Side.IMMUNE_SYSTEM, count.get(Side.IMMUNE_SYSTEM));
            }
            if (count.get(Side.IMMUNE_SYSTEM) == 0) {
                return Pair.of(Side.INFECTION, count.get(Side.INFECTION));
            }
        }
    }

    enum Side {
        IMMUNE_SYSTEM,
        INFECTION;
    }

    /**
     * --- Day 24: Immune System Simulator 20XX ---
     *
     * After a weird buzzing noise, you appear back at the man's cottage. He seems
     * relieved to see his friend, but quickly notices that the little reindeer
     * caught some kind of cold while out exploring.
     *
     * The portly man explains that this reindeer's immune system isn't similar to
     * regular reindeer immune systems:
     *
     * The immune system and the infection each have an army made up of several
     * groups; each group consists of one or more identical units. The armies
     * repeatedly fight until only one army has units remaining.
     *
     * Units within a group all have the same hit points (amount of damage a unit
     * can take before it is destroyed), attack damage (the amount of damage each
     * unit deals), an attack type, an initiative (higher initiative units attack
     * first and win ties), and sometimes weaknesses or immunities. Here is an
     * example group:
     *
     * 18 units each with 729 hit points (weak to fire; immune to cold, slashing)
     *  with an attack that does 8 radiation damage at initiative 10
     *
     * Each group also has an effective power: the number of units in that group
     * multiplied by their attack damage. The above group has an effective power
     * of 18 * 8 = 144. Groups never have zero or negative units; instead, the
     * group is removed from combat.
     *
     * Each fight consists of two phases: target selection and attacking.
     *
     * During the target selection phase, each group attempts to choose one
     * target. In decreasing order of effective power, groups choose their
     * targets; in a tie, the group with the higher initiative chooses first. The
     * attacking group chooses to target the group in the enemy army to which it
     * would deal the most damage (after accounting for weaknesses and immunities,
     * but not accounting for whether the defending group has enough units to
     * actually receive all of that damage).
     *
     * If an attacking group is considering two defending groups to which it would
     * deal equal damage, it chooses to target the defending group with the
     * largest effective power; if there is still a tie, it chooses the defending
     * group with the highest initiative. If it cannot deal any defending groups
     * damage, it does not choose a target. Defending groups can only be chosen as
     * a target by one attacking group.
     *
     * At the end of the target selection phase, each group has selected zero or
     * one groups to attack, and each group is being attacked by zero or one
     * groups.
     *
     * During the attacking phase, each group deals damage to the target it
     * selected, if any. Groups attack in decreasing order of initiative,
     * regardless of whether they are part of the infection or the immune system.
     * (If a group contains no units, it cannot attack.)
     *
     * The damage an attacking group deals to a defending group depends on the
     * attacking group's attack type and the defending group's immunities and
     * weaknesses. By default, an attacking group would deal damage equal to its
     * effective power to the defending group. However, if the defending group is
     * immune to the attacking group's attack type, the defending group instead
     * takes no damage; if the defending group is weak to the attacking group's
     * attack type, the defending group instead takes double damage.
     *
     * The defending group only loses whole units from damage; damage is always
     * dealt in such a way that it kills the most units possible, and any
     * remaining damage to a unit that does not immediately kill it is ignored.
     * For example, if a defending group contains 10 units with 10 hit points each
     * and receives 75 damage, it loses exactly 7 units and is left with 3 units
     * at full health.
     *
     * After the fight is over, if both armies still contain units, a new fight
     * begins; combat only ends once one army has lost all of its units.
     *
     * For example, consider the following armies:
     *
     * Immune System:
     * 17 units each with 5390 hit points (weak to radiation, bludgeoning) with
     *  an attack that does 4507 fire damage at initiative 2
     * 989 units each with 1274 hit points (immune to fire; weak to bludgeoning,
     *  slashing) with an attack that does 25 slashing damage at initiative 3
     *
     * Infection:
     * 801 units each with 4706 hit points (weak to radiation) with an attack
     *  that does 116 bludgeoning damage at initiative 1
     * 4485 units each with 2961 hit points (immune to radiation; weak to fire,
     *  cold) with an attack that does 12 slashing damage at initiative 4
     *
     * If these armies were to enter combat, the following fights, including
     * details during the target selection and attacking phases, would take place:
     *
     * Immune System:
     * Group 1 contains 17 units
     * Group 2 contains 989 units
     * Infection:
     * Group 1 contains 801 units
     * Group 2 contains 4485 units
     *
     * Infection group 1 would deal defending group 1 185832 damage
     * Infection group 1 would deal defending group 2 185832 damage
     * Infection group 2 would deal defending group 2 107640 damage
     * Immune System group 1 would deal defending group 1 76619 damage
     * Immune System group 1 would deal defending group 2 153238 damage
     * Immune System group 2 would deal defending group 1 24725 damage
     *
     * Infection group 2 attacks defending group 2, killing 84 units
     * Immune System group 2 attacks defending group 1, killing 4 units
     * Immune System group 1 attacks defending group 2, killing 51 units
     * Infection group 1 attacks defending group 1, killing 17 units
     *
     * Immune System:
     * Group 2 contains 905 units
     * Infection:
     * Group 1 contains 797 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 184904 damage
     * Immune System group 2 would deal defending group 1 22625 damage
     * Immune System group 2 would deal defending group 2 22625 damage
     *
     * Immune System group 2 attacks defending group 1, killing 4 units
     * Infection group 1 attacks defending group 2, killing 144 units
     *
     * Immune System:
     * Group 2 contains 761 units
     * Infection:
     * Group 1 contains 793 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 183976 damage
     * Immune System group 2 would deal defending group 1 19025 damage
     * Immune System group 2 would deal defending group 2 19025 damage
     *
     * Immune System group 2 attacks defending group 1, killing 4 units
     * Infection group 1 attacks defending group 2, killing 143 units
     *
     * Immune System:
     * Group 2 contains 618 units
     * Infection:
     * Group 1 contains 789 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 183048 damage
     * Immune System group 2 would deal defending group 1 15450 damage
     * Immune System group 2 would deal defending group 2 15450 damage
     *
     * Immune System group 2 attacks defending group 1, killing 3 units
     * Infection group 1 attacks defending group 2, killing 143 units
     *
     * Immune System:
     * Group 2 contains 475 units
     * Infection:
     * Group 1 contains 786 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 182352 damage
     * Immune System group 2 would deal defending group 1 11875 damage
     * Immune System group 2 would deal defending group 2 11875 damage
     *
     * Immune System group 2 attacks defending group 1, killing 2 units
     * Infection group 1 attacks defending group 2, killing 142 units
     *
     * Immune System:
     * Group 2 contains 333 units
     * Infection:
     * Group 1 contains 784 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 181888 damage
     * Immune System group 2 would deal defending group 1 8325 damage
     * Immune System group 2 would deal defending group 2 8325 damage
     *
     * Immune System group 2 attacks defending group 1, killing 1 unit
     * Infection group 1 attacks defending group 2, killing 142 units
     *
     * Immune System:
     * Group 2 contains 191 units
     * Infection:
     * Group 1 contains 783 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 181656 damage
     * Immune System group 2 would deal defending group 1 4775 damage
     * Immune System group 2 would deal defending group 2 4775 damage
     *
     * Immune System group 2 attacks defending group 1, killing 1 unit
     * Infection group 1 attacks defending group 2, killing 142 units
     *
     * Immune System:
     * Group 2 contains 49 units
     * Infection:
     * Group 1 contains 782 units
     * Group 2 contains 4434 units
     *
     * Infection group 1 would deal defending group 2 181424 damage
     * Immune System group 2 would deal defending group 1 1225 damage
     * Immune System group 2 would deal defending group 2 1225 damage
     *
     * Immune System group 2 attacks defending group 1, killing 0 units
     * Infection group 1 attacks defending group 2, killing 49 units
     *
     * Immune System:
     * No groups remain.
     * Infection:
     * Group 1 contains 782 units
     * Group 2 contains 4434 units
     *
     * In the example above, the winning army ends up with 782 + 4434 = 5216
     * units.
     *
     * You scan the reindeer's condition (your puzzle input); the white-bearded
     * man looks nervous. As it stands now, how many units would the winning army
     * have?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static Integer immuneSystemSimulator(Scanner scanner) {
            List<Unit> units = readUnits(scanner);

            Pair<Side, Integer> battle = battle(units, 0);
            LOGGER.info("battle = {}", battle);
            return battle.right();
        }
    }

    /**
     * --- Part Two ---
     *
     * Things aren't looking good for the reindeer. The man asks whether more milk
     * and cookies would help you think.
     *
     * If only you could give the reindeer's immune system a boost, you might be
     * able to change the outcome of the combat.
     *
     * A boost is an integer increase in immune system units' attack damage. For
     * example, if you were to boost the above example's immune system's units by
     * 1570, the armies would instead look like this:
     *
     * Immune System:
     * 17 units each with 5390 hit points (weak to radiation, bludgeoning) with
     *  an attack that does 6077 fire damage at initiative 2
     * 989 units each with 1274 hit points (immune to fire; weak to bludgeoning,
     *  slashing) with an attack that does 1595 slashing damage at initiative 3
     *
     * Infection:
     * 801 units each with 4706 hit points (weak to radiation) with an attack
     *  that does 116 bludgeoning damage at initiative 1
     * 4485 units each with 2961 hit points (immune to radiation; weak to fire,
     *  cold) with an attack that does 12 slashing damage at initiative 4
     *
     * With this boost, the combat proceeds differently:
     *
     * Immune System:
     * Group 2 contains 989 units
     * Group 1 contains 17 units
     * Infection:
     * Group 1 contains 801 units
     * Group 2 contains 4485 units
     *
     * Infection group 1 would deal defending group 2 185832 damage
     * Infection group 1 would deal defending group 1 185832 damage
     * Infection group 2 would deal defending group 1 53820 damage
     * Immune System group 2 would deal defending group 1 1577455 damage
     * Immune System group 2 would deal defending group 2 1577455 damage
     * Immune System group 1 would deal defending group 2 206618 damage
     *
     * Infection group 2 attacks defending group 1, killing 9 units
     * Immune System group 2 attacks defending group 1, killing 335 units
     * Immune System group 1 attacks defending group 2, killing 32 units
     * Infection group 1 attacks defending group 2, killing 84 units
     *
     * Immune System:
     * Group 2 contains 905 units
     * Group 1 contains 8 units
     * Infection:
     * Group 1 contains 466 units
     * Group 2 contains 4453 units
     *
     * Infection group 1 would deal defending group 2 108112 damage
     * Infection group 1 would deal defending group 1 108112 damage
     * Infection group 2 would deal defending group 1 53436 damage
     * Immune System group 2 would deal defending group 1 1443475 damage
     * Immune System group 2 would deal defending group 2 1443475 damage
     * Immune System group 1 would deal defending group 2 97232 damage
     *
     * Infection group 2 attacks defending group 1, killing 8 units
     * Immune System group 2 attacks defending group 1, killing 306 units
     * Infection group 1 attacks defending group 2, killing 29 units
     *
     * Immune System:
     * Group 2 contains 876 units
     * Infection:
     * Group 2 contains 4453 units
     * Group 1 contains 160 units
     *
     * Infection group 2 would deal defending group 2 106872 damage
     * Immune System group 2 would deal defending group 2 1397220 damage
     * Immune System group 2 would deal defending group 1 1397220 damage
     *
     * Infection group 2 attacks defending group 2, killing 83 units
     * Immune System group 2 attacks defending group 2, killing 427 units
     *
     * After a few fights...
     *
     * Immune System:
     * Group 2 contains 64 units
     * Infection:
     * Group 2 contains 214 units
     * Group 1 contains 19 units
     *
     * Infection group 2 would deal defending group 2 5136 damage
     * Immune System group 2 would deal defending group 2 102080 damage
     * Immune System group 2 would deal defending group 1 102080 damage
     *
     * Infection group 2 attacks defending group 2, killing 4 units
     * Immune System group 2 attacks defending group 2, killing 32 units
     *
     * Immune System:
     * Group 2 contains 60 units
     * Infection:
     * Group 1 contains 19 units
     * Group 2 contains 182 units
     *
     * Infection group 1 would deal defending group 2 4408 damage
     * Immune System group 2 would deal defending group 1 95700 damage
     * Immune System group 2 would deal defending group 2 95700 damage
     *
     * Immune System group 2 attacks defending group 1, killing 19 units
     *
     * Immune System:
     * Group 2 contains 60 units
     * Infection:
     * Group 2 contains 182 units
     *
     * Infection group 2 would deal defending group 2 4368 damage
     * Immune System group 2 would deal defending group 2 95700 damage
     *
     * Infection group 2 attacks defending group 2, killing 3 units
     * Immune System group 2 attacks defending group 2, killing 30 units
     *
     * After a few more fights...
     *
     * Immune System:
     * Group 2 contains 51 units
     * Infection:
     * Group 2 contains 40 units
     *
     * Infection group 2 would deal defending group 2 960 damage
     * Immune System group 2 would deal defending group 2 81345 damage
     *
     * Infection group 2 attacks defending group 2, killing 0 units
     * Immune System group 2 attacks defending group 2, killing 27 units
     *
     * Immune System:
     * Group 2 contains 51 units
     * Infection:
     * Group 2 contains 13 units
     *
     * Infection group 2 would deal defending group 2 312 damage
     * Immune System group 2 would deal defending group 2 81345 damage
     *
     * Infection group 2 attacks defending group 2, killing 0 units
     * Immune System group 2 attacks defending group 2, killing 13 units
     *
     * Immune System:
     * Group 2 contains 51 units
     * Infection:
     * No groups remain.
     *
     * This boost would allow the immune system's armies to win! It would be left
     * with 51 units.
     *
     * You don't even know how you could boost the reindeer's immune system or
     * what effect it might have, so you need to be cautious and find the smallest
     * boost that would allow the immune system to win.
     *
     * How many units does the immune system have left after getting the smallest
     * boost it needs to win?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static Integer immuneSystemSimulator(Scanner scanner) {
            List<Unit> units = readUnits(scanner);

            for (int boost = 0; ; ++boost) {
                Pair<Side, Integer> battle = battle(units, boost);
                LOGGER.info("battle = {}", battle);
                if (battle.left() == Side.IMMUNE_SYSTEM) {
                    return battle.right();
                }
            }
        }
    }

    record Unit(String id, MutableInt n, int hp, Set<String> immune, Set<String> weak,
                int init, String dtype, int dmg, Side side, AtomicReference<Unit> target) {
        Unit(String id, int n, int hp, Set<String> immune, Set<String> weak, int init, String dtype, int dmg, Side side) {
            this(id, new MutableInt(n), hp, immune, weak, init, dtype, dmg, side, new AtomicReference<>());
        }

        int power() {
            return n.intValue() * dmg;
        }

        int damageTo(Unit v) {
            if (v.immune.contains(dtype)) {
                return 0;
            } else if (v.weak.contains(dtype)) {
                return 2 * power();
            } else {
                return power();
            }
        }

        Unit withDamage(int dmg) {
            return new Unit(id, new MutableInt(n), hp, immune, weak, init, dtype, dmg, side, target);
        }

        static int comparePower(Unit u, Unit v) {
            int compare = Integer.compare(-u.power(), -v.power());
            if (compare != 0) {
                return compare;
            }

            return Integer.compare(-u.init, -v.init);
        }

        @Override
        public String toString() {
            return id + " power=" + power() + " init=" + init;
        }
    }

    record Attack(Unit target, int dmg, int power, int init) {
        static int compareAttack(Attack a, Attack b) {
            int compare = Integer.compare(-a.dmg, -b.dmg);
            if (compare != 0) {
                return compare;
            }

            compare = Integer.compare(-a.power, -b.power);
            if (compare != 0) {
                return compare;
            }

            return Integer.compare(-a.init, -b.init);
        }
    }
}
