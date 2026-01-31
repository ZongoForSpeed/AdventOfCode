package com.adventofcode.common.maths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DigitsTest {

    @Test
    void digits_INTEGER() {
        int[] digits = Digits.digits(3825336).intStream().toArray();
        assertThat(digits).containsExactly(3, 8, 2, 5, 3, 3, 6);
        assertThat(Digits.digits(0).intStream().toArray()).containsExactly(0);
    }

    @Test
    void digits_LONG() {
        int[] digits = Digits.digits(221280540398419L).intStream().toArray();
        assertThat(digits).containsExactly(2, 2, 1, 2, 8, 0, 5, 4, 0, 3, 9, 8, 4, 1, 9);
        assertThat(Digits.digits(0L).intStream().toArray()).containsExactly(0);
    }

    @Test
    void numberDigits_LONG() {
        assertThat(Digits.numberDigits(221280540398419L)).isEqualTo(15);
        assertThat(Digits.numberDigits(0L)).isEqualTo(0);
        assertThat(Digits.numberDigits(7L)).isEqualTo(1);
    }

}