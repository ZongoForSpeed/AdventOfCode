package com.adventofcode.common.graph;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Connectivity {

    private Connectivity() {
        // No-Op
    }

    public static <T> Set<T> connectedPoints(Map<T, ? extends Collection<T>> graph, T point) {
        Set<T> connected = new HashSet<>();
        connected.add(point);

        Deque<T> nodes = new ArrayDeque<>();
        nodes.add(point);

        while (!nodes.isEmpty()) {
            T d = nodes.remove();
            Collection<T> list = Objects.requireNonNull(graph.get(d), "Cannot find node '" + d + "' in graph");
            for (T p : list) {
                if (connected.add(p)) {
                    nodes.add(p);
                }
            }
        }
        return connected;
    }

    public static <T> Map<T, Set<T>> findRegion(Collection<T> points, Map<T, ? extends Collection<T>> graph) {
        Map<T, Set<T>> region = new HashMap<>();

        Set<T> visited = new HashSet<>();
        for (T point : points) {
            if (!visited.contains(point)) {
                Set<T> connected = connectedPoints(graph, point);
                visited.addAll(connected);

                region.put(point, connected);
            }
        }
        return region;
    }
}
