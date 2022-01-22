package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test {

    @Test
    void inputExample() {
        assertThat(Day16.dragonChecksum("10000", 20)).isEqualTo("01100");
    }

    @Test
    void inputPartOne() {
        assertThat(Day16.dragonChecksum("10111100110001111", 272)).isEqualTo("11100110111101110");
    }

    @Test
    void inputPartTwo() {
        assertThat(Day16.dragonChecksum("10111100110001111", 35651584)).isEqualTo("10001101010000101");
    }

}
