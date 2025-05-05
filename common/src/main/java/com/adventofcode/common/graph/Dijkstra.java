package com.adventofcode.common.graph;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Dijkstra<E> {
    private final Map<E, List<Pair<E, Integer>>> graph;

    public Dijkstra(Map<E, List<Pair<E, Integer>>> graph) {
        this.graph = graph;
    }

    public Object2IntMap<E> computeDistance(E start) {
        Object2IntMap<E> distance = new Object2IntOpenHashMap<>();
        distance.put(start, 0);

        Set<E> nodes = new HashSet<>(graph.keySet());

        while (!nodes.isEmpty()) {
            E next = null;
            int minimum = Integer.MAX_VALUE;
            for (E node : nodes) {
                if (distance.getOrDefault(node, Integer.MAX_VALUE) < minimum) {
                    next = node;
                    minimum = distance.getOrDefault(node, Integer.MAX_VALUE);
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
