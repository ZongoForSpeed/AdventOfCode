package com.adventofcode.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
        Queue<Node<E>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::cost));
        queue.add(new Node<>(start, 0L));
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                Collection<E> moves = graph.apply(node.vertex());
                for (E move : moves) {
                    if (!closedList.contains(move)) {
                        Node<E> suivant = new Node<>(move, node.cost() + distance.apply(node.vertex(), move));
                        queue.add(suivant);
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    public static <E> long algorithm(Function<E, Collection<Move<E>>> graph, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<Node<E>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::cost));
        queue.add(new Node<>(start, 0L));
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                Collection<Move<E>> moves = graph.apply(node.vertex());
                for (Move<E> move : moves) {
                    E vertex = move.vertex();
                    if (!closedList.contains(vertex)) {
                        Node<E> suivant = new Node<>(vertex, node.cost() + move.cost());
                        queue.add(suivant);
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    private record Node<E>(E vertex, long cost) {
    }

    public record Move<E>(E vertex, long cost) {
        public static <S> Move<S> of(S vertex, long cost) {
            return new Move<>(vertex, cost);
        }
    }
}