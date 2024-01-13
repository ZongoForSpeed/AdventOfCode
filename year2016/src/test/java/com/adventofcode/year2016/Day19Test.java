package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day19Test {

    @Test
    void inputExample() {
        assertThat(Day19.getLastElfPartOne(5)).isEqualTo(3);
        assertThat(Day19.getLastElfPartTwo(5)).isEqualTo(2);
    }

    @Test
    void inputPartOne() {
        assertThat(Day19.getLastElfPartOne(3014603)).isEqualTo(1834903);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day19.getLastElfPartTwo(3014603)).isEqualTo(1420280);
    }

}
