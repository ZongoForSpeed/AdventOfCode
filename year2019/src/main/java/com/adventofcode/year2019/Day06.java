package com.adventofcode.year2019;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day06 {

    private Day06() {
        // No-Op
    }

    /**
     * --- Day 6: Universal Orbit Map ---
     * You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves
     * transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example,
     * you and Santa. You download a map of the local orbits (your puzzle input).
     * <p>
     * Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object.
     * An orbit looks roughly like this:
     * <p>
     * \
     * \
     * |
     * |
     * AAA--> o            o <--BBB
     * |
     * |
     * /
     * /
     * <p>
     * In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is
     * only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit
     * around AAA".
     * <p>
     * Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download.
     * To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits
     * (like the one shown above) and indirect orbits.
     * <p>
     * Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if
     * A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.
     * <p>
     * For example, suppose you have the following map:
     * <p>
     * COM)B
     * B)C
     * C)D
     * D)E
     * E)F
     * B)G
     * G)H
     * D)I
     * E)J
     * J)K
     * K)L
     * Visually, the above map of orbits looks like this:
     * <p>
     * G - H       J - K - L
     * /           /
     * COM - B - C - D - E - F
     * \
     * I
     * In this visual representation, when two objects are connected by a line, the one on the right directly orbits
     * the one on the left.
     * <p>
     * Here, we can count the total number of orbits as follows:
     * <p>
     * D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
     * L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
     * COM orbits nothing.
     * The total number of direct and indirect orbits in this example is 42.
     * <p>
     * What is the total number of direct and indirect orbits in your map data?
     */
    static long internalCountOrbits(Map<String, String> graph, Map<String, Long> cache, String object) {
        Long value = cache.get(object);
        if (value != null) {
            return value;
        }

        long result = 0;
        String orbit = graph.get(object);
        if (orbit != null) {
            result += 1;
            result += internalCountOrbits(graph, cache, orbit);
        }

        cache.put(object, result);
        return result;
    }

    static long countOrbits(Map<String, String> graph) {
        long result = 0;

        Map<String, Long> cache = new HashMap<>();
        for (String object : graph.keySet()) {
            result += internalCountOrbits(graph, cache, object);
        }

        return result;
    }

    private static final Pattern PATTERN = Pattern.compile("(\\w+)\\)(\\w+)");

    public static Map<String, String> readGraph(Scanner scanner) {
        Map<String, String> graph = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                graph.put(matcher.group(2), matcher.group(1));
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        return graph;
    }

    /**
     * --- Part Two ---
     * Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).
     * <p>
     * You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer
     * lets you move from any object to an object orbiting or orbited by that object.
     * <p>
     * For example, suppose you have the following map:
     * <p>
     * COM)B
     * B)C
     * C)D
     * D)E
     * E)F
     * B)G
     * G)H
     * D)I
     * E)J
     * J)K
     * K)L
     * K)YOU
     * I)SAN
     * Visually, the above map of orbits looks like this:
     * <p>
     * YOU
     * /
     * G - H       J - K - L
     * /           /
     * COM - B - C - D - E - F
     * \
     * I - SAN
     * In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4
     * orbital transfers are required:
     * <p>
     * K to J
     * J to E
     * E to D
     * D to I
     * Afterward, the map of orbits looks like this:
     * <p>
     * G - H       J - K - L
     * /           /
     * COM - B - C - D - E - F
     * \
     * I - SAN
     * \
     * YOU
     * What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object
     * SAN is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)
     * <p>
     * Although it hasn't changed, you can still get your puzzle input.
     */
    private static Set<String> findParents(Map<String, String> graph, String node) {
        Set<String> nodes = new HashSet<>();
        String parent = graph.get(node);
        while (parent != null) {
            nodes.add(parent);
            parent = graph.get(parent);
        }

        return nodes;
    }

    static int countOrbitalTransfers(Map<String, String> graph, String node1, String node2) {
        Set<String> parents1 = findParents(graph, node1);
        Set<String> parents2 = findParents(graph, node2);
        Sets.SetView<String> difference12 = Sets.difference(parents1, parents2);
        Sets.SetView<String> difference21 = Sets.difference(parents2, parents1);
        return difference12.size() + difference21.size();
    }

}
