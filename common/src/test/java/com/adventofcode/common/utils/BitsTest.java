package com.adventofcode.common.utils;

import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class BitsTest {
    @Test
    void testConvertBitSet() {
        assertThat((Iterable<Integer>) Bits.convertBitSet(255L)).containsExactly(0, 1, 2, 3, 4, 5, 6, 7);
        assertThat((Iterable<Integer>) Bits.convertBitSet(92L)).containsExactly(2, 3, 4, 6);
        assertThat((Iterable<Integer>) Bits.convertBitSet(1000000L)).containsExactly(6, 9, 14, 16, 17, 18, 19);
        assertThat((Iterable<Integer>) Bits.convertBitSet(12345L)).containsExactly(0, 3, 4, 5, 12, 13);
        assertThat((Iterable<Integer>) Bits.convertBitSet(0L)).isEmpty();
    }

    @Test
    void testToBitSetFromList() {
        assertThat(Bits.toBitSet(IntList.of(0, 1, 2, 3, 4, 5, 6, 7))).isEqualTo(255L);
        assertThat(Bits.toBitSet(IntList.of(2, 3, 4, 6))).isEqualTo(92L);
        assertThat(Bits.toBitSet(IntList.of(6, 9, 14, 16, 17, 18, 19))).isEqualTo(1000000L);
        assertThat(Bits.toBitSet(IntList.of(0, 3, 4, 5, 12, 13))).isEqualTo(12345L);
    }

    @Test
    void testToBitSetFromStream() {
        assertThat(Bits.toBitSet(IntStream.of(0, 1, 2, 3, 4, 5, 6, 7))).isEqualTo(255L);
        assertThat(Bits.toBitSet(IntStream.of(2, 3, 4, 6))).isEqualTo(92L);
    }

    @Test
    void testContains() {
        long bitset = 0b1011L;
        assertThat(Bits.contains(bitset, 0)).isTrue();
        assertThat(Bits.contains(bitset, 1)).isTrue();
        assertThat(Bits.contains(bitset, 2)).isFalse();
        assertThat(Bits.contains(bitset, 3)).isTrue();
        assertThat(Bits.contains(bitset, 4)).isFalse();
    }

    @Test
    void testAdd() {
        long bitset = 0b1011L;
        assertThat(Bits.add(bitset, 2)).isEqualTo(0b1111L);
        assertThat(Bits.add(bitset, 0)).isEqualTo(0b1011L);
    }

    @Test
    void testRemove() {
        long bitset = 0b1011L;
        assertThat(Bits.remove(bitset, 1)).isEqualTo(0b1001L);
        assertThat(Bits.remove(bitset, 2)).isEqualTo(0b1011L);
    }
}