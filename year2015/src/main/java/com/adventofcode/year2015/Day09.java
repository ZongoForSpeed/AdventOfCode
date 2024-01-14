package com.adventofcode.year2015;

import com.adventofcode.common.maths.Permutations;
import com.adventofcode.common.utils.IntegerPair;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day09 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09.class);
    private static final Pattern PATTERN = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

    private Day09() {
        // No-Op
    }

    /**
     * --- Day 9: All in a Single Night ---
     *
     * Every year, Santa manages to deliver all of his presents in a single night.
     *
     * This year, however, he has some new locations to visit; his elves have
     * provided him the distances between every pair of locations. He can start
     * and end at any two (different) locations he wants, but he must visit each
     * location exactly once. What is the shortest distance he can travel to
     * achieve this?
     *
     * For example, given the following distances:
     *
     * London to Dublin = 464
     * London to Belfast = 518
     * Dublin to Belfast = 141
     *
     * The possible routes are therefore:
     *
     * Dublin -> London -> Belfast = 982
     * London -> Dublin -> Belfast = 605
     * London -> Belfast -> Dublin = 659
     * Dublin -> Belfast -> London = 659
     * Belfast -> Dublin -> London = 605
     * Belfast -> London -> Dublin = 982
     *
     * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
     *
     * What is the distance of the shortest route?
     *
     * Your puzzle answer was 141.
     *
     * --- Part Two ---
     *
     * The next year, just to show off, Santa decides to take the route with the
     * longest distance instead.
     *
     * He can still start and end at any two (different) locations he wants, and
     * he still must visit each location exactly once.
     *
     * For example, given the distances above, the longest route would be 982 via
     * (for example) Dublin -> London -> Belfast.
     *
     * What is the distance of the longest route?
     *
     * Your puzzle answer was 736.
     */
    public static IntegerPair computeBestDistance(Scanner scanner) {
        Map<Pair<String, String>, Integer> graph = new HashMap<>();
        Set<String> cities = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int distance = Integer.parseInt(matcher.group(3));
                String city1 = matcher.group(1);
                String city2 = matcher.group(2);
                LOGGER.info("{} -> {} = {}", city1, city2, distance);
                graph.put(Pair.of(city1, city2), distance);
                graph.put(Pair.of(city2, city1), distance);
                cities.add(city1);
                cities.add(city2);
            }
        }

        LOGGER.info("Graph :\n{}", graph);
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;
        Iterator<List<String>> iterator = Permutations.of(cities).iterator();
        while (iterator.hasNext()) {
            List<String> permutation = iterator.next();
            OptionalInt distance = computeDistance(graph, permutation);
            if (distance.isPresent()) {
                int distanceAsInt = distance.getAsInt();
                if (distanceAsInt < minDistance) {
                    LOGGER.info("Cities: {} = {}", permutation, distance);
                    minDistance = distance.getAsInt();
                }
                if (distanceAsInt > maxDistance) {
                    LOGGER.info("Cities: {} = {}", permutation, distance);
                    maxDistance = distance.getAsInt();
                }
            }

        }
        return IntegerPair.of(minDistance, maxDistance);
    }

    public static OptionalInt computeDistance(Map<Pair<String, String>, Integer> graph, List<String> cities) {
        int distance = 0;
        for (int i = 1; i < cities.size(); i++) {
            String city1 = cities.get(i - 1);
            String city2 = cities.get(i);
            Integer cost = graph.get(Pair.of(city1, city2));
            if (cost == null) {
                LOGGER.warn("Couldn't find {} -> {}", city1, city2);
                return OptionalInt.empty();
            }
            distance += cost;
        }

        return OptionalInt.of(distance);
    }
}
