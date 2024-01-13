package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test {

    @Test
    void inputExample() {
        assertThat(Day15.countPairsPartOne(65, 8921)).isEqualTo(588);
        assertThat(Day15.countPairsPartTwo(65, 8921)).isEqualTo(309);
    }

    @Test
    void inputPartOne() {
        assertThat(Day15.countPairsPartOne(618, 814)).isEqualTo(577);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day15.countPairsPartTwo(618, 814)).isEqualTo(316);
    }
}
