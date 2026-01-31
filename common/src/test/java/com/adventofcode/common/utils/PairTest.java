package com.adventofcode.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PairTest {

    @Test
    void testBooleanPair() {
        BooleanPair pair = BooleanPair.of(true, false);
        assertThat(pair.left()).isTrue();
        assertThat(pair.right()).isFalse();
        assertThat(pair).isEqualTo(new BooleanPair(true, false));
        assertThat(pair).isNotEqualTo(BooleanPair.of(false, true));
    }

    @Test
    void testCharPair() {
        CharPair pair = CharPair.of('a', 'b');
        assertThat(pair.left()).isEqualTo('a');
        assertThat(pair.right()).isEqualTo('b');
        assertThat(pair.toString()).isEqualTo("(a,b)");
        assertThat(pair).isEqualTo(new CharPair('a', 'b'));
    }

    @Test
    void testIntegerPair() {
        IntegerPair pair = IntegerPair.of(1, 2);
        assertThat(pair.left()).isEqualTo(1);
        assertThat(pair.right()).isEqualTo(2);
        assertThat(pair.toString()).isEqualTo("(1,2)");
        assertThat(pair).isEqualTo(new IntegerPair(1, 2));
    }

    @Test
    void testLongPair() {
        LongPair pair = LongPair.of(1L, 2L);
        assertThat(pair.left()).isEqualTo(1L);
        assertThat(pair.right()).isEqualTo(2L);
        assertThat(pair.toString()).isEqualTo("(1,2)");
        assertThat(pair).isEqualTo(new LongPair(1L, 2L));
    }
}
