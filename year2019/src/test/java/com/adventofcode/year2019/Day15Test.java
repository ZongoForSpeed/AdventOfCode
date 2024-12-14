package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.common.graph.Dijkstra;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.Map2D;
import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test extends AbstractTest {
    Day15Test() {
        super(2019, 15);
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        Intcode.Robot robot = new Intcode.Robot(line);
        Point2D origin = Point2D.of(0, 0);

        Map2D map = new Map2D();
        Day15.cartography(robot, map, new ArrayDeque<>(), new HashSet<>(), origin);

        map.print(Day15::print);

        Point2D oxygen = map.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).findFirst().orElseThrow();
        Map<Point2D, List<Pair<Point2D, Integer>>> graph = Day15.createGraph(map);

        Dijkstra<Point2D> dijkstra = new Dijkstra<>(graph);

        Map<Point2D, Integer> distance = dijkstra.computeDistance(origin);
        assertThat(distance).containsEntry(oxygen, 240);
    }

    @Override
    public void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        Intcode.Robot robot = new Intcode.Robot(line);
        Point2D origin = Point2D.of(0, 0);

        Map2D map = new Map2D();
        Day15.cartography(robot, map, new ArrayDeque<>(), new HashSet<>(), origin);

        map.print(Day15::print);

        Point2D oxygen = map.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).findFirst().orElseThrow();
        Map<Point2D, List<Pair<Point2D, Integer>>> graph = Day15.createGraph(map);

        Dijkstra<Point2D> dijkstra = new Dijkstra<>(graph);

        Map<Point2D, Integer> oxygenFill = dijkstra.computeDistance(oxygen);
        int duration = oxygenFill.values().stream().mapToInt(x -> x).max().orElse(0);
        assertThat(duration).isEqualTo(322);
    }
}
