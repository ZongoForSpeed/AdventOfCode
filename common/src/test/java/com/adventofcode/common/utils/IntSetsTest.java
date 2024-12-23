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
}