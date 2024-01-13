package com.adventofcode.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AStar {
    /**
     * cf. https://fr.wikipedia.org/wiki/Algorithme_A*
     */
    public static <E> long algorithm(Function<E, Collection<E>> graph, BiFunction<E, E, Long> distance, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<NodeHeuristic<E>> queue = new PriorityQueue<>(Comparator.comparingLong(NodeHeuristic::cost));
        queue.add(new NodeHeuristic<>(start, 0L, 0L));
        while (!queue.isEmpty()) {
            NodeHeuristic<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                Collection<E> moves = graph.apply(node.vertex());
                for (E move : moves) {
                    if (!closedList.contains(move)) {
                        long cost = node.cost() + distance.apply(node.vertex(), move);
                        queue.add(new NodeHeuristic<>(move, cost, cost));
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    public static <E> long algorithmHeuristic(Function<E, List<E>> graph, BiFunction<E, E, Long> distance, BiFunction<E, E, Long> heuristic, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<NodeHeuristic<E>> queue = new PriorityQueue<>(Comparator.comparingLong(NodeHeuristic::heuristic));
        queue.add(new NodeHeuristic<>(start, 0L, distance.apply(start, end)));
        while (!queue.isEmpty()) {
            NodeHeuristic<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                List<E> moves = graph.apply(node.vertex());
                for (E move : moves) {
                    if (!closedList.contains(move)) {
                        NodeHeuristic<E> suivant = new NodeHeuristic<>(move, node.cost() + distance.apply(node.vertex(), move), node.cost() + heuristic.apply(move, end) + 1);
                        queue.add(suivant);
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    public static <E> long algorithm(Function<E, Collection<Move<E>>> graph, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<NodeHeuristic<E>> queue = new PriorityQueue<>(Comparator.comparingLong(NodeHeuristic::cost));
        queue.add(new NodeHeuristic<>(start, 0L, 0L));
        while (!queue.isEmpty()) {
            NodeHeuristic<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                Collection<Move<E>> moves = graph.apply(node.vertex());
                for (Move<E> move : moves) {
                    E vertex = move.vertex();
                    if (!closedList.contains(vertex)) {
                        long cost = node.cost() + move.cost();
                        queue.add(new NodeHeuristic<>(vertex, cost, cost));
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    private record NodeHeuristic<E>(E vertex, long cost, long heuristic) {
    }

    public record Move<E>(E vertex, long cost) {
        public static <S> Move<S> of(S vertex, long cost) {
            return new Move<>(vertex, cost);
        }
    }
}