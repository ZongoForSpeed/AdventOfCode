package com.adventofcode.common.graph;

import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DijkstraTest {

    @Test
    void testSimplePath() {
        // A -(2)-> B -(3)-> C
        var graph = Map.of(
                "A", List.of(Pair.of("B", 2)),
                "B", List.of(Pair.of("C", 3)),
                "C", List.of()
        );

        var dijkstra = new Dijkstra<String>(graph);
        var distances = dijkstra.computeDistance("A");

        assertThat(distances.getInt("A")).isEqualTo(0);
        assertThat(distances.getInt("B")).isEqualTo(2);
        assertThat(distances.getInt("C")).isEqualTo(5);
        assertThat(distances).hasSize(3);
    }

    @Test
    void testMultiplePaths() {
        // A -(1)-> B -(4)-> D
        // A -(3)-> C -(1)-> D
        var graph = Map.of(
                "A", List.of(Pair.of("B", 1), Pair.of("C", 3)),
                "B", List.of(Pair.of("D", 4)),
                "C", List.of(Pair.of("D", 1)),
                "D", List.of()
        );

        var dijkstra = new Dijkstra<String>(graph);
        var distances = dijkstra.computeDistance("A");

        assertThat(distances.getInt("A")).isEqualTo(0);
        assertThat(distances.getInt("B")).isEqualTo(1);
        assertThat(distances.getInt("C")).isEqualTo(3);
        assertThat(distances.getInt("D")).isEqualTo(4); // A -> C -> D is 3+1=4, A -> B -> D is 1+4=5
    }

    @Test
    void testGraphWithCycle() {
        // A -(1)-> B -(1)-> C -(1)-> A
        // B -(4)-> D
        var graph = Map.of(
                "A", List.of(Pair.of("B", 1)),
                "B", List.of(Pair.of("C", 1), Pair.of("D", 4)),
                "C", List.of(Pair.of("A", 1)),
                "D", List.of()
        );

        var dijkstra = new Dijkstra<String>(graph);
        var distances = dijkstra.computeDistance("A");

        assertThat(distances.getInt("A")).isEqualTo(0);
        assertThat(distances.getInt("B")).isEqualTo(1);
        assertThat(distances.getInt("C")).isEqualTo(2);
        assertThat(distances.getInt("D")).isEqualTo(5);
    }

    @Test
    void testDisconnectedGraph() {
        // A -(1)-> B
        // C -(1)-> D
        var graph = Map.of(
                "A", List.of(Pair.of("B", 1)),
                "B", List.of(),
                "C", List.of(Pair.of("D", 1)),
                "D", List.of()
        );

        var dijkstra = new Dijkstra<String>(graph);
        var distances = dijkstra.computeDistance("A");

        assertThat(distances.getInt("A")).isEqualTo(0);
        assertThat(distances.getInt("B")).isEqualTo(1);
        assertThat(distances.containsKey("C")).isFalse();
        assertThat(distances.containsKey("D")).isFalse();
    }

    @Test
    void testSingleNode() {
        var graph = Map.of(
                "A", List.of()
        );

        var dijkstra = new Dijkstra<String>(graph);
        var distances = dijkstra.computeDistance("A");

        assertThat(distances).hasSize(1);
        assertThat(distances.getInt("A")).isEqualTo(0);
    }
}
