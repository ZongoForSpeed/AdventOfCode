package com.adventofcode.common.graph;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public final class Dijkstra<E> {
    private final Map<E, List<Pair<E, Integer>>> graph;

    public Dijkstra(Map<E, List<Pair<E, Integer>>> graph) {
        this.graph = graph;
    }

    public Object2IntMap<E> computeDistance(E start) {
        var distance = new Object2IntOpenHashMap<E>();
        distance.defaultReturnValue(Integer.MAX_VALUE);
        distance.put(start, 0);

        var queue = new PriorityQueue<ObjectIntPair<E>>(Comparator.comparingInt(ObjectIntPair::rightInt));
        queue.add(ObjectIntPair.of(start, 0));
        while (!queue.isEmpty()) {
            var current = queue.remove();
            var node = current.left();
            var cost = current.rightInt();
            if (cost > distance.getInt(node)) {
                // Stale entry: a shorter path to this node was already processed
                continue;
            }
            for (Pair<E, Integer> edge : graph.getOrDefault(node, List.of())) {
                var newCost = cost + edge.right();
                if (newCost < distance.getInt(edge.left())) {
                    distance.put(edge.left(), newCost);
                    queue.add(ObjectIntPair.of(edge.left(), newCost));
                }
            }
        }
        distance.defaultReturnValue(0);
        return distance;
    }
}
