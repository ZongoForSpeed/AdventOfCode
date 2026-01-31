package com.adventofcode.common.utils;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.assertj.core.api.Assertions.assertThat;

class IntSetsTest {

    @Test
    void of() {
        BitSet bitSet = IntSets.of(6, 9, 14, 16, 17, 18, 19);
        assertThat(bitSet.stream().toArray()).containsExactly(6, 9, 14, 16, 17, 18, 19);
    }

    @Test
    void copy() {
        BitSet bitSet = IntSets.of(6, 9, 14, 16, 17, 18, 19);

        BitSet copy = IntSets.copy(bitSet);
        assertThat(copy).isEqualTo(bitSet).isNotSameAs(bitSet);
    }

    @Test
    void intersection() {
        BitSet bitSet1 = IntSets.of(6, 9, 14, 16, 17, 18, 19);
        BitSet bitSet2 = IntSets.of(0, 1, 2, 3, 4, 5, 6, 7);

        BitSet intersection = IntSets.intersection(bitSet1, bitSet2);
        assertThat(intersection.stream().toArray()).containsExactly(6);
    }

    @Test
    void union() {
        BitSet bitSet1 = IntSets.of(6, 9, 14, 16, 17, 18, 19);
        BitSet bitSet2 = IntSets.of(0, 1, 2, 3, 4, 5, 6, 7);

        BitSet union = IntSets.union(bitSet1, bitSet2);
        assertThat(union.stream().toArray()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 9, 14, 16, 17, 18, 19);
    }

    @Test
    void unionWithInt() {
        BitSet bitSet = IntSets.of(1, 2);
        BitSet result = IntSets.union(bitSet, 3);
        assertThat(result.stream().toArray()).containsExactly(1, 2, 3);
        assertThat(bitSet.stream().toArray()).containsExactly(1, 2);
    }

    @Test
    void empty() {
        assertThat(IntSets.empty().isEmpty()).isTrue();
    }

    @Test
    void range() {
        BitSet range = IntSets.range(1, 4);
        assertThat(range.stream().toArray()).containsExactly(1, 2, 3);
    }
}