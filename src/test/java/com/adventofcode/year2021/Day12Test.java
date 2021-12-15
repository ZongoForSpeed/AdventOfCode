package com.adventofcode.year2021;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static void findAllPathsPartOne(Map<String, List<String>> adjList, String current, String destination,
                                            Set<String> visited,
                                            Deque<String> currentPath, Set<List<String>> paths) {

        if (current.equals(destination)) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        if (StringUtils.isAllLowerCase(current)) {
            visited.add(current);
        }

        for (String i : adjList.getOrDefault(current, Collections.emptyList())) {
            if (StringUtils.isAllUpperCase(i) || !visited.contains(i)) {
                currentPath.addLast(i);
                findAllPathsPartOne(adjList, i, destination, visited, currentPath, paths);
                currentPath.removeLast();
            }
        }

        visited.remove(current);
    }

    private static Set<List<String>> findAllPathsPartOne(Scanner scanner) {
        Map<String, List<String>> graph = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split("-");
            graph.computeIfAbsent(split[0], ignore -> new ArrayList<>()).add(split[1]);
            graph.computeIfAbsent(split[1], ignore -> new ArrayList<>()).add(split[0]);
        }

        LOGGER.info("Graph : {}", graph);

        Deque<String> pathList = new ArrayDeque<>();
        pathList.add("start");

        Set<List<String>> paths = new HashSet<>();

        findAllPathsPartOne(graph, "start", "end", new HashSet<>(), pathList, paths);

        return paths;
    }


    private static void findAllPathsPartTwo(Map<String, List<String>> adjList, String current, String destination,
                                            Set<String> visited, AtomicReference<String> visitedTwice,
                                            Deque<String> currentPath, Set<List<String>> paths) {

        if (current.equals(destination)) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        if (StringUtils.isAllLowerCase(current)) {
            if (visited.contains(current)) {
                visitedTwice.set(current);
            } else {
                visited.add(current);
            }
        }

        for (String i : adjList.getOrDefault(current, Collections.emptyList())) {
            if (StringUtils.isAllUpperCase(i) || !visited.contains(i) || visitedTwice.get() == null) {
                currentPath.addLast(i);
                findAllPathsPartTwo(adjList, i, destination, visited, visitedTwice, currentPath, paths);
                currentPath.removeLast();
            }
        }

        if (visitedTwice.get() != null && visitedTwice.get().equals(current)) {
            visitedTwice.set(null);
        } else {
            visited.remove(current);
        }
    }

    private static Set<List<String>> findAllPathsPartTwo(Scanner scanner) {
        Map<String, List<String>> graph = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split("-");
            String a = split[0];
            String b = split[1];
            graph.computeIfAbsent(a, ignore -> new ArrayList<>()).add(b);
            graph.computeIfAbsent(b, ignore -> new ArrayList<>()).add(a);
        }

        graph.remove("end");
        graph.forEach((key, value) -> value.remove("start"));

        LOGGER.info("Graph : {}", graph);

        Deque<String> pathList = new ArrayDeque<>();
        pathList.add("start");

        Set<List<String>> paths = new HashSet<>();

        findAllPathsPartTwo(graph, "start", "end", new HashSet<>(), new AtomicReference<>(), pathList, paths);

        return paths;
    }


    @Test
    void inputSimpleExample() {
        String input = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end""";

        {
            Set<List<String>> paths = findAllPathsPartOne(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(10);
        }

        {
            Set<List<String>> paths = findAllPathsPartTwo(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(36);
        }
    }

    @Test
    void inputSlightlyLargerExample() {
        String input = """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc""";


        {
            Set<List<String>> paths = findAllPathsPartOne(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(19);
        }

        {
            Set<List<String>> paths = findAllPathsPartTwo(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(103);
        }
    }

    @Test
    void inputLargerExample() {
        String input = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW""";

        assertThat(findAllPathsPartOne(new Scanner(input))).hasSize(226);
        assertThat(findAllPathsPartTwo(new Scanner(input))).hasSize(3509);
    }


    /**
     * --- Day 12: Passage Pathing ---
     *
     * With your submarine's subterranean subsystems subsisting suboptimally, the
     * only way you're getting out of this cave anytime soon is by finding a path
     * yourself. Not just a path - the only way to know if you've found the best
     * path is to find all of them.
     *
     * Fortunately, the sensors are still mostly working, and so you build a rough
     * map of the remaining caves (your puzzle input). For example:
     *
     * start-A
     * start-b
     * A-c
     * A-b
     * b-d
     * A-end
     * b-end
     *
     * This is a list of how all of the caves are connected. You start in the cave
     * named start, and your destination is the cave named end. An entry like b-d
     * means that cave b is connected to cave d - that is, you can move between
     * them.
     *
     * So, the above cave system looks roughly like this:
     *
     *     start
     *     /   \
     * c--A-----b--d
     *     \   /
     *      end
     *
     * Your goal is to find the number of distinct paths that start at start, end
     * at end, and don't visit small caves more than once. There are two types of
     * caves: big caves (written in uppercase, like A) and small caves (written
     * in lowercase, like b). It would be a waste of time to visit any small cave
     * more than once, but big caves are large enough that it might be worth
     * visiting them multiple times. So, all paths you find should visit small
     * caves at most once, and can visit big caves any number of times.
     *
     * Given these rules, there are 10 paths through this example cave system:
     *
     * start,A,b,A,c,A,end
     * start,A,b,A,end
     * start,A,b,end
     * start,A,c,A,b,A,end
     * start,A,c,A,b,end
     * start,A,c,A,end
     * start,A,end
     * start,b,A,c,A,end
     * start,b,A,end
     * start,b,end
     *
     * (Each line in the above list corresponds to a single path; the caves
     * visited by that path are listed in the order they are visited and separated
     * by commas.)
     *
     * Note that in this cave system, cave d is never visited by any path: to do
     * so, cave b would need to be visited twice (once on the way to cave d and a
     * second time when returning from cave d), and since cave b is small, this
     * is not allowed.
     *
     * Here is a slightly larger example:
     *
     * dc-end
     * HN-start
     * start-kj
     * dc-start
     * dc-HN
     * LN-dc
     * HN-end
     * kj-sa
     * kj-HN
     * kj-dc
     *
     * The 19 paths through it are as follows:
     *
     * start,HN,dc,HN,end
     * start,HN,dc,HN,kj,HN,end
     * start,HN,dc,end
     * start,HN,dc,kj,HN,end
     * start,HN,end
     * start,HN,kj,HN,dc,HN,end
     * start,HN,kj,HN,dc,end
     * start,HN,kj,HN,end
     * start,HN,kj,dc,HN,end
     * start,HN,kj,dc,end
     * start,dc,HN,end
     * start,dc,HN,kj,HN,end
     * start,dc,end
     * start,dc,kj,HN,end
     * start,kj,HN,dc,HN,end
     * start,kj,HN,dc,end
     * start,kj,HN,end
     * start,kj,dc,HN,end
     * start,kj,dc,end
     *
     * Finally, this even larger example has 226 paths through it:
     *
     * fs-end
     * he-DX
     * fs-he
     * start-DX
     * pj-DX
     * end-zg
     * zg-sl
     * zg-pj
     * pj-he
     * RW-he
     * fs-DX
     * pj-RW
     * zg-RW
     * start-pj
     * he-WI
     * zg-he
     * pj-fs
     * start-RW
     *
     * How many paths through this cave system are there that visit small caves at
     * most once?
     *
     * Your puzzle answer was 5104.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2021/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(findAllPathsPartOne(scanner)).hasSize(5104);
        }
    }

    /**
     * --- Part Two ---
     *
     * After reviewing the available paths, you realize you might have time to
     * visit a single small cave twice. Specifically, big caves can be visited any
     * number of times, a single small cave can be visited at most twice, and the
     * remaining small caves can be visited at most once. However, the caves named
     * start and end can only be visited exactly once each: once you leave the
     * start cave, you may not return to it, and once you reach the end cave, the
     * path must end immediately.
     *
     * Now, the 36 possible paths through the first example above are:
     *
     * start,A,b,A,b,A,c,A,end
     * start,A,b,A,b,A,end
     * start,A,b,A,b,end
     * start,A,b,A,c,A,b,A,end
     * start,A,b,A,c,A,b,end
     * start,A,b,A,c,A,c,A,end
     * start,A,b,A,c,A,end
     * start,A,b,A,end
     * start,A,b,d,b,A,c,A,end
     * start,A,b,d,b,A,end
     * start,A,b,d,b,end
     * start,A,b,end
     * start,A,c,A,b,A,b,A,end
     * start,A,c,A,b,A,b,end
     * start,A,c,A,b,A,c,A,end
     * start,A,c,A,b,A,end
     * start,A,c,A,b,d,b,A,end
     * start,A,c,A,b,d,b,end
     * start,A,c,A,b,end
     * start,A,c,A,c,A,b,A,end
     * start,A,c,A,c,A,b,end
     * start,A,c,A,c,A,end
     * start,A,c,A,end
     * start,A,end
     * start,b,A,b,A,c,A,end
     * start,b,A,b,A,end
     * start,b,A,b,end
     * start,b,A,c,A,b,A,end
     * start,b,A,c,A,b,end
     * start,b,A,c,A,c,A,end
     * start,b,A,c,A,end
     * start,b,A,end
     * start,b,d,b,A,c,A,end
     * start,b,d,b,A,end
     * start,b,d,b,end
     * start,b,end
     *
     * The slightly larger example above now has 103 paths through it, and the
     * even larger example now has 3509 paths through it.
     *
     * Given these new rules, how many paths through this cave system are there?
     *
     * Your puzzle answer was 149220.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2021/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(findAllPathsPartTwo(scanner)).hasSize(149220);
        }
    }

}
