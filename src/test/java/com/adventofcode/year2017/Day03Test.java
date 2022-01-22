package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test {

    @Test
    void inputExample() {
        assertThat(Day03.spiralMemory(1)).isZero();
        assertThat(Day03.spiralMemory(12)).isEqualTo(3);
        assertThat(Day03.spiralMemory(23)).isEqualTo(2);
        assertThat(Day03.spiralMemory(1024)).isEqualTo(31);

        assertThat(Day03.spiralSum(20)).isEqualTo(23);
        assertThat(Day03.spiralSum(700)).isEqualTo(747);
    }

    @Test
    void inputPartOne() {
        assertThat(Day03.spiralMemory(265149)).isEqualTo(438);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day03.spiralSum(265149)).isEqualTo(266330);
    }

}
