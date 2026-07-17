package com.adventofcode.common.graph;

import java.util.ArrayDeque;
import java.util.Collection;
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
        var connected = new HashSet<T>();
        connected.add(point);

        var nodes = new ArrayDeque<T>();
        nodes.add(point);

        while (!nodes.isEmpty()) {
            T d = nodes.remove();
            var list = Objects.<T>requireNonNull(graph.get(d), "Cannot find node '" + d + "' in graph");
            for (T p : list) {
                if (connected.add(p)) {
                    nodes.add(p);
                }
            }
        }
        return connected;
    }

    public static <T> Map<T, Set<T>> findRegion(Collection<T> points, Map<T, ? extends Collection<T>> graph) {
        var region = new HashMap<T, Set<T>>();

        var visited = new HashSet<T>();
        for (T point : points) {
            if (!visited.contains(point)) {
                var connected = <T>connectedPoints(graph, point);
                visited.addAll(connected);

                region.put(point, connected);
            }
        }
        return region;
    }
}
