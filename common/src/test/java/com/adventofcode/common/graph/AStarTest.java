package com.adventofcode.common.graph;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AStarTest {

    private static class TestAStar extends AStar<String> {
        private final Map<String, List<Move<String>>> graph;
        private final Map<String, Long> heuristics;

        TestAStar(Map<String, List<Move<String>>> graph) {
            this(graph, Collections.emptyMap());
        }

        TestAStar(Map<String, List<Move<String>>> graph, Map<String, Long> heuristics) {
            this.graph = graph;
            this.heuristics = heuristics;
        }

        @Override
        public Iterable<Move<String>> next(String node) {
            return graph.getOrDefault(node, Collections.emptyList());
        }

        @Override
        public long heuristic(String node) {
            return heuristics.getOrDefault(node, 0L);
        }
    }

    @Test
    void testSimplePath() {
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 1), AStar.Move.of("C", 4)),
                "B", List.of(AStar.Move.of("C", 2))
        );
        TestAStar astar = new TestAStar(graph);

        assertThat(astar.algorithm("A", "C")).isEqualTo(3);
    }

    @Test
    void testMultiplePaths() {
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 1), AStar.Move.of("D", 10)),
                "B", List.of(AStar.Move.of("C", 1)),
                "C", List.of(AStar.Move.of("D", 1))
        );
        TestAStar astar = new TestAStar(graph);

        assertThat(astar.algorithm("A", "D")).isEqualTo(3);
    }

    @Test
    void testHeuristic() {
        // Graph where the path through B is shorter, but C looks better to a greedy search
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 5), AStar.Move.of("C", 2)),
                "B", List.of(AStar.Move.of("Goal", 1)),
                "C", List.of(AStar.Move.of("Goal", 10))
        );
        // Heuristic that favors B
        Map<String, Long> heuristics = Map.of(
                "B", 1L,
                "C", 20L
        );
        TestAStar astar = new TestAStar(graph, heuristics);

        assertThat(astar.algorithm("A", "Goal")).isEqualTo(6);
    }

    @Test
    void testDisconnectedGraph() {
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 1))
        );
        TestAStar astar = new TestAStar(graph);

        assertThat(astar.algorithm("A", "C")).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void testPredicateEnding() {
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 1), AStar.Move.of("C", 5)),
                "B", List.of(AStar.Move.of("D", 2)),
                "C", List.of(AStar.Move.of("E", 1))
        );
        TestAStar astar = new TestAStar(graph);

        // Find any node starting with 'E'
        assertThat(astar.algorithm("A", node -> node.startsWith("E"))).isEqualTo(6);
    }

    @Test
    void testMoveOfS() {
        AStar.Move<String> move = AStar.Move.of("Target");
        assertThat(move.vertex()).isEqualTo("Target");
        assertThat(move.cost()).isEqualTo(1L);
    }

    @Test
    void testCycle() {
        Map<String, List<AStar.Move<String>>> graph = Map.of(
                "A", List.of(AStar.Move.of("B", 1)),
                "B", List.of(AStar.Move.of("A", 1), AStar.Move.of("C", 1))
        );
        TestAStar astar = new TestAStar(graph);

        assertThat(astar.algorithm("A", "C")).isEqualTo(2);
    }
}
