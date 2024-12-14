package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day11Test {

    @Test
    void inputExample() {
        assertThat(Day11.example()).isEqualTo(11);
    }

    @Test
    void inputPartOne() {
        assertThat(Day11.partOne()).isEqualTo(33);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day11.partTwo()).isEqualTo(57);
    }

}
