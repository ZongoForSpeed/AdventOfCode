package com.adventofcode.year2022;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day19 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);

    private static final Pattern BLUEPRINT_PATTERN = Pattern.compile("Blueprint (\\d+): (.*)\\.");
    private static final Pattern ROBOTSS_PATTERN = Pattern.compile("Each (\\w+) robot costs (.*)");

    /**
     * --- Day 19: Not Enough Minerals ---
     *
     * Your scans show that the lava did indeed form obsidian!
     *
     * The wind has changed direction enough to stop sending lava droplets toward
     * you, so you and the elephants exit the cave. As you do, you notice a
     * collection of geodes around the pond. Perhaps you could use the obsidian to
     * create some geode-cracking robots and break them open?
     *
     * To collect the obsidian from the bottom of the pond, you'll need waterproof
     * obsidian-collecting robots. Fortunately, there is an abundant amount of
     * clay nearby that you can use to make them waterproof.
     *
     * In order to harvest the clay, you'll need special-purpose clay-collecting
     * robots. To make any type of robot, you'll need ore, which is also plentiful
     * but in the opposite direction from the clay.
     *
     * Collecting ore requires ore-collecting robots with big drills. Fortunately,
     * you have exactly one ore-collecting robot in your pack that you can use to
     * kickstart the whole operation.
     *
     * Each robot can collect 1 of its resource type per minute. It also takes one
     * minute for the robot factory (also conveniently from your pack) to
     * construct any type of robot, although it consumes the necessary resources
     * available when construction begins.
     *
     * The robot factory has many blueprints (your puzzle input) you can choose
     * from, but once you've configured it with a blueprint, you can't change it.
     * You'll need to work out which blueprint is best.
     *
     * For example:
     *
     * Blueprint 1:
     *   Each ore robot costs 4 ore.
     *   Each clay robot costs 2 ore.
     *   Each obsidian robot costs 3 ore and 14 clay.
     *   Each geode robot costs 2 ore and 7 obsidian.
     *
     * Blueprint 2:
     *   Each ore robot costs 2 ore.
     *   Each clay robot costs 3 ore.
     *   Each obsidian robot costs 3 ore and 8 clay.
     *   Each geode robot costs 3 ore and 12 obsidian.
     *
     * (Blueprints have been line-wrapped here for legibility. The robot factory's
     * actual assortment of blueprints are provided one blueprint per line.)
     *
     * The elephants are starting to look hungry, so you shouldn't take too long;
     * you need to figure out which blueprint would maximize the number of opened
     * geodes after 24 minutes by figuring out which robots to build and when to
     * build them.
     *
     * Using blueprint 1 in the example above, the largest number of geodes you
     * could open in 24 minutes is 9. One way to achieve that is:
     *
     * == Minute 1 ==
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     *
     * == Minute 2 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     *
     * == Minute 3 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * The new clay-collecting robot is ready; you now have 1 of them.
     *
     * == Minute 4 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 1 clay-collecting robot collects 1 clay; you now have 1 clay.
     *
     * == Minute 5 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * 1 clay-collecting robot collects 1 clay; you now have 2 clay.
     * The new clay-collecting robot is ready; you now have 2 of them.
     *
     * == Minute 6 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 2 clay-collecting robots collect 2 clay; you now have 4 clay.
     *
     * == Minute 7 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * 2 clay-collecting robots collect 2 clay; you now have 6 clay.
     * The new clay-collecting robot is ready; you now have 3 of them.
     *
     * == Minute 8 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 9 clay.
     *
     * == Minute 9 ==
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 12 clay.
     *
     * == Minute 10 ==
     * 1 ore-collecting robot collects 1 ore; you now have 4 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 15 clay.
     *
     * == Minute 11 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 4 clay.
     * The new obsidian-collecting robot is ready; you now have 1 of them.
     *
     * == Minute 12 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 7 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 1 obsidian.
     * The new clay-collecting robot is ready; you now have 4 of them.
     *
     * == Minute 13 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 11 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 2 obsidian.
     *
     * == Minute 14 ==
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 15 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 3 obsidian.
     *
     * == Minute 15 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 5 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 4 obsidian.
     * The new obsidian-collecting robot is ready; you now have 2 of them.
     *
     * == Minute 16 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 9 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 6 obsidian.
     *
     * == Minute 17 ==
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 13 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 8 obsidian.
     *
     * == Minute 18 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 17 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 3 obsidian.
     * The new geode-cracking robot is ready; you now have 1 of them.
     *
     * == Minute 19 ==
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 21 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 5 obsidian.
     * 1 geode-cracking robot cracks 1 geode; you now have 1 open geode.
     *
     * == Minute 20 ==
     * 1 ore-collecting robot collects 1 ore; you now have 4 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 25 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 7 obsidian.
     * 1 geode-cracking robot cracks 1 geode; you now have 2 open geodes.
     *
     * == Minute 21 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 29 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 2 obsidian.
     * 1 geode-cracking robot cracks 1 geode; you now have 3 open geodes.
     * The new geode-cracking robot is ready; you now have 2 of them.
     *
     * == Minute 22 ==
     * 1 ore-collecting robot collects 1 ore; you now have 4 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 33 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 4 obsidian.
     * 2 geode-cracking robots crack 2 geodes; you now have 5 open geodes.
     *
     * == Minute 23 ==
     * 1 ore-collecting robot collects 1 ore; you now have 5 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 37 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 6 obsidian.
     * 2 geode-cracking robots crack 2 geodes; you now have 7 open geodes.
     *
     * == Minute 24 ==
     * 1 ore-collecting robot collects 1 ore; you now have 6 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 41 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 8 obsidian.
     * 2 geode-cracking robots crack 2 geodes; you now have 9 open geodes.
     *
     * However, by using blueprint 2 in the example above, you could do even
     * better: the largest number of geodes you could open in 24 minutes is 12.
     *
     * Determine the quality level of each blueprint by multiplying that
     * blueprint's ID number with the largest number of geodes that can be opened
     * in 24 minutes using that blueprint. In this example, the first blueprint
     * has ID 1 and can open 9 geodes, so its quality level is 9. The second
     * blueprint has ID 2 and can open 12 geodes, so its quality level is 24.
     * Finally, if you add up the quality levels of all of the blueprints in the
     * list, you get 33.
     *
     * Determine the quality level of each blueprint using the largest number of
     * geodes it could produce in 24 minutes. What do you get if you add up the
     * quality level of all of the blueprints in your list?
     */
    public static long computeQualityLevelPartOne(Scanner scanner) {
        List<Blueprint> blueprints = readInput(scanner, false);

        long qualityLevel = 0;

        for (Blueprint blueprint : blueprints) {
            long robotFactory = blueprint.maximizeRobotFactory(24);
            LOGGER.info("maximizeRobotFactory {} ==> {}", blueprint.id(), robotFactory);
            qualityLevel += robotFactory * blueprint.id();
        }
        return qualityLevel;
    }

    /**
     * --- Part Two ---
     *
     * While you were choosing the best blueprint, the elephants found some food
     * on their own, so you're not in as much of a hurry; you figure you probably
     * have 32 minutes before the wind changes direction again and you'll need to
     * get out of range of the erupting volcano.
     *
     * Unfortunately, one of the elephants ate most of your blueprint list! Now,
     * only the first three blueprints in your list are intact.
     *
     * In 32 minutes, the largest number of geodes blueprint 1 (from the example
     * above) can open is 56. One way to achieve that is:
     *
     * == Minute 1 ==
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     *
     * == Minute 2 ==
     * 1 ore-collecting robot collects 1 ore; you now have 2 ore.
     *
     * == Minute 3 ==
     * 1 ore-collecting robot collects 1 ore; you now have 3 ore.
     *
     * == Minute 4 ==
     * 1 ore-collecting robot collects 1 ore; you now have 4 ore.
     *
     * == Minute 5 ==
     * Spend 4 ore to start building an ore-collecting robot.
     * 1 ore-collecting robot collects 1 ore; you now have 1 ore.
     * The new ore-collecting robot is ready; you now have 2 of them.
     *
     * == Minute 6 ==
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     *
     * == Minute 7 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * The new clay-collecting robot is ready; you now have 1 of them.
     *
     * == Minute 8 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 1 clay-collecting robot collects 1 clay; you now have 1 clay.
     * The new clay-collecting robot is ready; you now have 2 of them.
     *
     * == Minute 9 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 2 clay-collecting robots collect 2 clay; you now have 3 clay.
     * The new clay-collecting robot is ready; you now have 3 of them.
     *
     * == Minute 10 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 3 clay-collecting robots collect 3 clay; you now have 6 clay.
     * The new clay-collecting robot is ready; you now have 4 of them.
     *
     * == Minute 11 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 4 clay-collecting robots collect 4 clay; you now have 10 clay.
     * The new clay-collecting robot is ready; you now have 5 of them.
     *
     * == Minute 12 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 5 clay-collecting robots collect 5 clay; you now have 15 clay.
     * The new clay-collecting robot is ready; you now have 6 of them.
     *
     * == Minute 13 ==
     * Spend 2 ore to start building a clay-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 6 clay-collecting robots collect 6 clay; you now have 21 clay.
     * The new clay-collecting robot is ready; you now have 7 of them.
     *
     * == Minute 14 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 14 clay.
     * The new obsidian-collecting robot is ready; you now have 1 of them.
     *
     * == Minute 15 ==
     * 2 ore-collecting robots collect 2 ore; you now have 4 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 21 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 1 obsidian.
     *
     * == Minute 16 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 14 clay.
     * 1 obsidian-collecting robot collects 1 obsidian; you now have 2 obsidian.
     * The new obsidian-collecting robot is ready; you now have 2 of them.
     *
     * == Minute 17 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 7 clay.
     * 2 obsidian-collecting robots collect 2 obsidian; you now have 4 obsidian.
     * The new obsidian-collecting robot is ready; you now have 3 of them.
     *
     * == Minute 18 ==
     * 2 ore-collecting robots collect 2 ore; you now have 4 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 14 clay.
     * 3 obsidian-collecting robots collect 3 obsidian; you now have 7 obsidian.
     *
     * == Minute 19 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 7 clay.
     * 3 obsidian-collecting robots collect 3 obsidian; you now have 10 obsidian.
     * The new obsidian-collecting robot is ready; you now have 4 of them.
     *
     * == Minute 20 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 3 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 14 clay.
     * 4 obsidian-collecting robots collect 4 obsidian; you now have 7 obsidian.
     * The new geode-cracking robot is ready; you now have 1 of them.
     *
     * == Minute 21 ==
     * Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 7 clay.
     * 4 obsidian-collecting robots collect 4 obsidian; you now have 11 obsidian.
     * 1 geode-cracking robot cracks 1 geode; you now have 1 open geode.
     * The new obsidian-collecting robot is ready; you now have 5 of them.
     *
     * == Minute 22 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 14 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 9 obsidian.
     * 1 geode-cracking robot cracks 1 geode; you now have 2 open geodes.
     * The new geode-cracking robot is ready; you now have 2 of them.
     *
     * == Minute 23 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 21 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 7 obsidian.
     * 2 geode-cracking robots crack 2 geodes; you now have 4 open geodes.
     * The new geode-cracking robot is ready; you now have 3 of them.
     *
     * == Minute 24 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 2 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 28 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 5 obsidian.
     * 3 geode-cracking robots crack 3 geodes; you now have 7 open geodes.
     * The new geode-cracking robot is ready; you now have 4 of them.
     *
     * == Minute 25 ==
     * 2 ore-collecting robots collect 2 ore; you now have 4 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 35 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 10 obsidian.
     * 4 geode-cracking robots crack 4 geodes; you now have 11 open geodes.
     *
     * == Minute 26 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 4 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 42 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 8 obsidian.
     * 4 geode-cracking robots crack 4 geodes; you now have 15 open geodes.
     * The new geode-cracking robot is ready; you now have 5 of them.
     *
     * == Minute 27 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 4 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 49 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 6 obsidian.
     * 5 geode-cracking robots crack 5 geodes; you now have 20 open geodes.
     * The new geode-cracking robot is ready; you now have 6 of them.
     *
     * == Minute 28 ==
     * 2 ore-collecting robots collect 2 ore; you now have 6 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 56 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 11 obsidian.
     * 6 geode-cracking robots crack 6 geodes; you now have 26 open geodes.
     *
     * == Minute 29 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 6 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 63 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 9 obsidian.
     * 6 geode-cracking robots crack 6 geodes; you now have 32 open geodes.
     * The new geode-cracking robot is ready; you now have 7 of them.
     *
     * == Minute 30 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 6 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 70 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 7 obsidian.
     * 7 geode-cracking robots crack 7 geodes; you now have 39 open geodes.
     * The new geode-cracking robot is ready; you now have 8 of them.
     *
     * == Minute 31 ==
     * Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
     * 2 ore-collecting robots collect 2 ore; you now have 6 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 77 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 5 obsidian.
     * 8 geode-cracking robots crack 8 geodes; you now have 47 open geodes.
     * The new geode-cracking robot is ready; you now have 9 of them.
     *
     * == Minute 32 ==
     * 2 ore-collecting robots collect 2 ore; you now have 8 ore.
     * 7 clay-collecting robots collect 7 clay; you now have 84 clay.
     * 5 obsidian-collecting robots collect 5 obsidian; you now have 10 obsidian.
     * 9 geode-cracking robots crack 9 geodes; you now have 56 open geodes.
     *
     * However, blueprint 2 from the example above is still better; using it, the
     * largest number of geodes you could open in 32 minutes is 62.
     *
     * You no longer have enough blueprints to worry about quality levels.
     * Instead, for each of the first three blueprints, determine the largest
     * number of geodes you could open; then, multiply these three values
     * together.
     *
     * Don't worry about quality levels; instead, just determine the largest
     * number of geodes you could open using each of the first three blueprints.
     * What do you get if you multiply these numbers together?
     */
    public static long computeQualityLevelPartTwo(Scanner scanner) {
        List<Blueprint> blueprints = readInput(scanner, true);

        long qualityLevel = 1;

        for (Blueprint blueprint : blueprints) {
            long robotFactory = blueprint.maximizeRobotFactory(32);
            LOGGER.info("maximizeRobotFactory {} ==> {}", blueprint.id(), robotFactory);
            qualityLevel *= robotFactory;
        }
        return qualityLevel;
    }

    private static List<Blueprint> readInput(Scanner scanner, boolean skip) {
        List<Blueprint> blueprints = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = BLUEPRINT_PATTERN.matcher(line);
            if (matcher.matches()) {
                int blueprint = Integer.parseInt(matcher.group(1));
                LOGGER.info("Blueprint = {}", blueprint);
                int[][] costs = new int[Resource.values().length][Resource.values().length];
                for (String s : Splitter.on(". ").split(matcher.group(2))) {
                    LOGGER.info("Robot = '{}'", s);
                    Matcher matcher1 = ROBOTSS_PATTERN.matcher(s);
                    if (matcher1.matches()) {
                        Resource resource = Resource.valueOf(matcher1.group(1));
                        Arrays.stream(matcher1.group(2).split(" and "))
                                .map(c -> c.split(" "))
                                .forEach(a -> costs[resource.ordinal()][Resource.valueOf(a[1]).ordinal()] = Integer.parseInt(a[0]));
                        LOGGER.info("{} ==> {}", resource, toString(costs[resource.ordinal()]));
                    }
                }
                blueprints.add(new Blueprint(blueprint, costs));
            }
            if (skip && blueprints.size() == 3) {
                break;
            }
        }

        LOGGER.info("blueprints => {}", blueprints);
        return blueprints;
    }

    private static String toString(int[] table) {
        return "{" + Arrays.stream(Resource.values())
                .map(r -> r + ": " + table[r.ordinal()])
                .collect(Collectors.joining(", ")) + "}";
    }

    private enum Resource {
        ore,
        clay,
        obsidian,
        geode;
    }

    private record Blueprint(long id, int[][] costs) {

        public List<Resource> canBuild(ResourceState resources) {
            List<Resource> robots = new ArrayList<>();
            for (Resource value : Resource.values()) {
                if (canBuild(resources, costs[value.ordinal()])) {
                    robots.add(value);
                }
            }
            return robots;
        }

        public ResourceState build(ResourceState resources, Resource robot) {
            int[] cost = costs[robot.ordinal()];
            return new ResourceState(
                    resources.ore - cost[Resource.ore.ordinal()],
                    resources.clay - cost[Resource.clay.ordinal()],
                    resources.obsidian - cost[Resource.obsidian.ordinal()],
                    resources.geode - cost[Resource.geode.ordinal()]
            );
        }

        private static boolean canBuild(ResourceState resources, int[] cost) {
            for (Resource resource : Resource.values()) {
                if (resources.get(resource) < cost[resource.ordinal()]) {
                    return false;
                }
            }
            return true;
        }

        long maximizeRobotFactory(int time) {
            List<State> states = List.of(new State(new ResourceState(0, 0, 0, 0), new RobotState(1, 0, 0, 0)));

            for (int t = 1; t <= time; ++t) {
                List<State> newStates = new ArrayList<>();
                for (State state : states) {
                    ResourceState resources = state.resources();
                    RobotState robots = state.robots();

                    List<Resource> canBuild = this.canBuild(resources);
                    ResourceState increaseResources = resources.collects(robots);
                    newStates.add(new State(increaseResources, robots));

                    for (Resource robot : canBuild) {
                        ResourceState newResources = this.build(increaseResources, robot);
                        RobotState newRobots = robots.build(robot);
                        newStates.add(new State(newResources, newRobots));
                    }
                }

                states = newStates.stream().distinct().sorted(Comparator.reverseOrder()).limit(10_000).toList();
            }

            return states.stream().map(State::resources).mapToInt(ResourceState::geode).max().orElseThrow();
        }
    }

    private record ResourceState(int ore, int clay, int obsidian, int geode) implements Comparable<ResourceState> {
        ResourceState collects(RobotState robots) {
            return new ResourceState(ore + robots.ore,
                    clay + robots.clay,
                    obsidian + robots.obsidian,
                    geode + robots.geode);
        }

        int get(Resource resource) {
            return switch (resource) {
                case ore -> ore;
                case clay -> clay;
                case obsidian -> obsidian;
                case geode -> geode;
            };
        }

        @Override
        public int compareTo(ResourceState o) {
            int compare = Integer.compare(geode, o.geode);
            if (compare != 0) {
                return compare;
            }
            compare = Integer.compare(obsidian, o.obsidian);
            if (compare != 0) {
                return compare;
            }
            compare = Integer.compare(clay, o.clay);
            if (compare != 0) {
                return compare;
            }
            return Integer.compare(ore, o.ore);
        }
    }

    private record RobotState(int ore, int clay, int obsidian, int geode) implements Comparable<RobotState> {
        RobotState build(Resource robot) {
            return switch (robot) {
                case ore -> new RobotState(ore + 1, clay, obsidian, geode);
                case clay -> new RobotState(ore, clay + 1, obsidian, geode);
                case obsidian -> new RobotState(ore, clay, obsidian + 1, geode);
                case geode -> new RobotState(ore, clay, obsidian, geode + 1);
            };
        }

        @Override
        public int compareTo(RobotState o) {
            int compare = Integer.compare(geode, o.geode);
            if (compare != 0) {
                return compare;
            }
            compare = Integer.compare(obsidian, o.obsidian);
            if (compare != 0) {
                return compare;
            }
            compare = Integer.compare(clay, o.clay);
            if (compare != 0) {
                return compare;
            }
            return Integer.compare(ore, o.ore);
        }
    }

    record State(ResourceState resources, RobotState robots, ResourceState nextResources) implements Comparable<State> {
        State(ResourceState resources, RobotState robots) {
            this(resources, robots, resources.collects(robots));
        }

        @Override
        public int compareTo(State o) {
            int compare = nextResources.compareTo(o.nextResources);
            if (compare != 0) {
                return compare;
            }
            return robots.compareTo(o.robots);
        }
    }
}
