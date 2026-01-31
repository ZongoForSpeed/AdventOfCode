package com.adventofcode.common.graph;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConnectivityTest {

    @Test
    void testConnectedPointsSimple() {
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A", "C"),
                "C", List.of("B")
        );

        Set<String> connected = Connectivity.connectedPoints(graph, "A");
        assertThat(connected).containsExactlyInAnyOrder("A", "B", "C");
    }

    @Test
    void testConnectedPointsDisconnected() {
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A"),
                "C", List.of("D"),
                "D", List.of("C")
        );

        assertThat(Connectivity.connectedPoints(graph, "A")).containsExactlyInAnyOrder("A", "B");
        assertThat(Connectivity.connectedPoints(graph, "C")).containsExactlyInAnyOrder("C", "D");
    }

    @Test
    void testConnectedPointsSingleNode() {
        Map<String, List<String>> graph = Map.of("A", List.of());

        Set<String> connected = Connectivity.connectedPoints(graph, "A");
        assertThat(connected).containsExactly("A");
    }

    @Test
    void testConnectedPointsMissingNode() {
        Map<String, List<String>> graph = Map.of("A", List.of("B"));

        // B is in A's list, but B is not a key in the graph
        assertThatThrownBy(() -> Connectivity.connectedPoints(graph, "A"))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Cannot find node 'B' in graph");
    }

    @Test
    void testFindRegionSingleComponent() {
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A", "C"),
                "C", List.of("B")
        );
        List<String> points = List.of("A", "B", "C");

        Map<String, Set<String>> regions = Connectivity.findRegion(points, graph);
        
        assertThat(regions).hasSize(1);
        assertThat(regions.get("A")).containsExactlyInAnyOrder("A", "B", "C");
    }

    @Test
    void testFindRegionMultipleComponents() {
        Map<String, List<String>> graph = Map.of(
                "A", List.of("B"),
                "B", List.of("A"),
                "C", List.of("D"),
                "D", List.of("C"),
                "E", List.of()
        );
        List<String> points = List.of("A", "B", "C", "D", "E");

        Map<String, Set<String>> regions = Connectivity.findRegion(points, graph);

        assertThat(regions).hasSize(3);
        // The representative of each region depends on the iteration order of 'points'
        // Since points is a List, order is guaranteed: A, C, E should be the keys if they were not visited
        assertThat(regions.get("A")).containsExactlyInAnyOrder("A", "B");
        assertThat(regions.get("C")).containsExactlyInAnyOrder("C", "D");
        assertThat(regions.get("E")).containsExactlyInAnyOrder("E");
    }

    @Test
    void testFindRegionEmptyPoints() {
        Map<String, List<String>> graph = Map.of("A", List.of());
        Collection<String> points = List.of();

        Map<String, Set<String>> regions = Connectivity.findRegion(points, graph);
        assertThat(regions).isEmpty();
    }
}
