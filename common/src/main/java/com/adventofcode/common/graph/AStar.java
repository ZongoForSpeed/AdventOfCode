package com.adventofcode.common.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * cf. https://fr.wikipedia.org/wiki/Algorithme_A*
 */
public abstract class AStar<E> {

    public abstract Iterable<Move<E>> next(E node);

    public long algorithm(E start, E end) {
        return algorithm(start, end::equals);
    }

    public long heuristic(E node) {
        return 0L;
    }

    public long algorithm(E start, Predicate<E> ending) {
        Set<E> closedList = new HashSet<>();
        Queue<NodeHeuristic<E>> queue = new PriorityQueue<>(Comparator.comparingLong(NodeHeuristic::cost));
        queue.add(new NodeHeuristic<>(start, 0L, heuristic(start)));
        while (!queue.isEmpty()) {
            NodeHeuristic<E> node = queue.poll();
            if (ending.test(node.vertex())) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                Iterable<Move<E>> moves = next(node.vertex());
                for (Move<E> move : moves) {
                    E vertex = move.vertex();
                    if (!closedList.contains(vertex)) {
                        long cost = node.cost() + move.cost();
                        queue.add(new NodeHeuristic<>(vertex, cost, cost + heuristic(vertex)));
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

        public static <S> Move<S> of(S vertex) {
            return new Move<>(vertex, 1L);
        }
    }
}
