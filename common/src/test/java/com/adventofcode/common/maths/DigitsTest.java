package com.adventofcode.common.maths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DigitsTest {

    @Test
    void digits_INTEGER() {
        int[] digits = Digits.digits(3825336).intStream().toArray();
        Assertions.assertThat(digits).containsExactly(3, 8, 2, 5, 3, 3, 6);
    }

    @Test
    void digits_LONG() {
        int[] digits = Digits.digits(221280540398419L).intStream().toArray();
        Assertions.assertThat(digits).containsExactly(2, 2, 1, 2, 8, 0, 5, 4, 0, 3, 9, 8, 4, 1, 9);
    }

    @Test
    void numberDigits_LONG() {
        Assertions.assertThat(Digits.numberDigits(221280540398419L)).isEqualTo(15);
    }

}