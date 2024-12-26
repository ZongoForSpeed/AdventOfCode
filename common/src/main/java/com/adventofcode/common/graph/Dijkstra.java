package com.adventofcode.common.graph;

import it.unimi.dsi.fastutil.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Dijkstra<E> {
    private final Map<E, List<Pair<E, Integer>>> graph;

    public Dijkstra(Map<E, List<Pair<E, Integer>>> graph) {
        this.graph = graph;
    }

    public Map<E, Integer> computeDistance(E start) {
        Map<E, Integer> distance = new HashMap<>();
        distance.put(start, 0);

        Set<E> nodes = new HashSet<>(graph.keySet());

        while (!nodes.isEmpty()) {
            E next = null;
            int minimum = Integer.MAX_VALUE;
            for (E node : nodes) {
                if (distance.getOrDefault(node, Integer.MAX_VALUE) < minimum) {
                    next = node;
                    minimum = distance.get(node);
                }
            }

            if (next == null) {
                break;
            }

            nodes.remove(next);

            for (Pair<E, Integer> edge : graph.getOrDefault(next, Collections.emptyList())) {
                if (distance.getOrDefault(edge.left(), Integer.MAX_VALUE) > minimum + edge.right()) {
                    distance.put(edge.left(), minimum + edge.right());
                }
            }
        }
        return distance;
    }
}
