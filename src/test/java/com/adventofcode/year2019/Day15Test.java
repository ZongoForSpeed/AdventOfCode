package com.adventofcode.year2019;

import com.adventofcode.Intcode;
import com.adventofcode.graph.Dijkstra;
import com.adventofcode.point.map.Map2D;
import com.adventofcode.point.Point2D;
import com.adventofcode.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    @Test
    void testCartography() throws IOException {
        String line = FileUtils.readLine("/2019/day/15/input");
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

        Map<Point2D, Integer> oxygenFill = dijkstra.computeDistance(oxygen);
        int duration = oxygenFill.values().stream().mapToInt(x -> x).max().orElse(0);
        assertThat(duration).isEqualTo(322);
    }
}
