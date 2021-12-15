package com.adventofcode.graph;

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
    public static <E> long algorithm(Function<E, List<E>> graph, BiFunction<E, E, Long> distance, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<Node<E>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::cost));
        queue.add(new Node<>(start, 0L, Integer.MAX_VALUE));
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                List<E> moves = graph.apply(node.vertex());
                for (E move : moves) {
                    if (!closedList.contains(move)) {
                        Node<E> suivant = new Node<>(move, node.cost() + distance.apply(node.vertex(), move), Integer.MAX_VALUE);
                        queue.add(suivant);
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    public static <E> long algorithm(Function<E, List<E>> graph, BiFunction<E, E,  Long> distance, BiFunction<E, E,  Long> heuristic, E start, E end) {
        Set<E> closedList = new HashSet<>();
        Queue<Node<E>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::heuristic));
        queue.add(new Node<>(start, 0L, heuristic.apply(start, end)));
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (node.vertex().equals(end)) {
                return node.cost();
            }
            if (closedList.add(node.vertex())) {
                List<E> moves = graph.apply(node.vertex());
                for (E move : moves) {
                    if (!closedList.contains(move)) {
                        Node<E> suivant = new Node<>(move, node.cost() + distance.apply(node.vertex(), move), node.cost() + heuristic.apply(end, move) + 1);
                        queue.add(suivant);
                    }
                }
            }
        }

        return Long.MAX_VALUE;
    }

    private record Node<E>(E vertex, long cost, long heuristic) {
    }
}