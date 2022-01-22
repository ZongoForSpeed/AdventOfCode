package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day20Test {

    private final Day20 day20;

    Day20Test() {
        this.day20 = new Day20();
    }

    @Test
    void inputExample() {
        assertThat(day20.countPresent(1)).isEqualTo(10);
        assertThat(day20.countPresent(4)).isEqualTo(70);
        assertThat(day20.countPresent(6)).isEqualTo(120);
    }

    @Test
    void inputPartOne() {
        assertThat(day20.findLowestHouseNumberPartOne(29000000)).isEqualTo(665280);
    }

    @Test
    void inputPartTwo() {
        assertThat(day20.findLowestHouseNumberPartTwo(29000000)).isEqualTo(705600);
    }
}
