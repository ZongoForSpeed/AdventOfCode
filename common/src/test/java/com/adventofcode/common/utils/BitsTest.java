package com.adventofcode.common.utils;

import com.adventofcode.common.utils.Bits;
import it.unimi.dsi.fastutil.ints.IntList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BitsTest {
    @Test
    void convertBitSet() {
        Assertions.assertThat(Bits.convertBitSet(255L).toIntArray()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7);
        assertThat(Bits.convertBitSet(92L).toIntArray()).containsExactly(2, 3, 4, 6);
        assertThat(Bits.convertBitSet(1000000L).toIntArray()).containsExactly(6, 9, 14, 16, 17, 18, 19);
        assertThat(Bits.convertBitSet(12345L).toIntArray()).containsExactly(0, 3, 4, 5, 12, 13);
    }

    @Test
    void toBitSet() {
        assertThat(Bits.toBitSet(IntList.of(0, 1, 2, 3, 4, 5, 6, 7))).isEqualTo(255L);
        assertThat(Bits.toBitSet(IntList.of(2, 3, 4, 6))).isEqualTo(92);
        assertThat(Bits.toBitSet(IntList.of(6, 9, 14, 16, 17, 18, 19))).isEqualTo(1000000L);
        assertThat(Bits.toBitSet(IntList.of(0, 3, 4, 5, 12, 13))).isEqualTo(12345L);
    }
}