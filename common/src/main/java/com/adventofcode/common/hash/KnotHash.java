package com.adventofcode.common.hash;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class KnotHash {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnotHash.class);

    private KnotHash() {
        // No-Op
    }

    public static int sparseHash(String input, int endExclusive) {
        IntStream intStream = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt);
        IntList integers = sparseHash(IntArrayList.toList(intStream), endExclusive, 1);
        return integers.getInt(0) * integers.getInt(1);
    }

    @SuppressWarnings("UnusedCollectionModifiedInPlace")
    private static IntList sparseHash(IntList lengths, int endExclusive, int steps) {
        IntList elements = IntArrayList.toList(IntStream.range(0, endExclusive));

        int skipSize = 0;
        int position = 0;
        for (int step = 0; step < steps; ++step) {
            for (int length : lengths) {
                Collections.reverse(new CircularSubList<>(elements, position, position + length));
                position += length + skipSize;
                position %= elements.size();
                skipSize++;
                LOGGER.trace("elements : {}, position={}, skipSize={}", elements, position, skipSize);
            }
        }
        return elements;
    }

    public static String knotHash(String input) {
        IntArrayList lengths = IntArrayList.toList(input.chars());
        lengths.addAll(IntList.of(17, 31, 73, 47, 23));

        IntList sparseHash = sparseHash(lengths, 256, 64);

        final int size = sparseHash.size();
        String denseHash = IntStream.range(0, (size + 15) >> 4)
                .map(i -> hashXOR(sparseHash, i << 4, (i + 1) << 4))
                .mapToObj("%02x"::formatted)
                .collect(Collectors.joining());

        LOGGER.info("knotHash of '{}': {}", input, denseHash);
        return denseHash;
    }

    private static int hashXOR(IntList sparseHash, int fromIndex, int toIndex) {
        return sparseHash.subList(fromIndex, toIndex).intStream().reduce((a, b) -> a ^ b).orElseThrow();
    }

    private static class CircularSubList<E> extends AbstractList<E> {
        private final List<E> root;
        private final int fromIndex;
        private final int toIndex;

        private CircularSubList(List<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public E get(int index) {
            return root.get((index + fromIndex) % root.size());
        }

        @Override
        public E set(int index, E element) {
            return root.set((index + fromIndex) % root.size(), element);
        }

        @Override
        public int size() {
            return toIndex - fromIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CircularSubList<?> that)) return false;
            if (!super.equals(o)) return false;
            return fromIndex == that.fromIndex && toIndex == that.toIndex && Objects.equals(root, that.root);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), root, fromIndex, toIndex);
        }
    }
}
