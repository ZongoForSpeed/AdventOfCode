package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day14Test {

    @Test
    void inputExample() {
        assertThat(Day14.chocolateChartsPartOne(9).toIntArray()).containsExactly(5, 1, 5, 8, 9, 1, 6, 7, 7, 9);
        assertThat(Day14.chocolateChartsPartOne(10).toIntArray()).containsExactly(1, 5, 8, 9, 1, 6, 7, 7, 9, 2);
        assertThat(Day14.chocolateChartsPartOne(5).toIntArray()).containsExactly(0, 1, 2, 4, 5, 1, 5, 8, 9, 1);
        assertThat(Day14.chocolateChartsPartOne(18).toIntArray()).containsExactly(9, 2, 5, 1, 0, 7, 1, 0, 8, 5);
        assertThat(Day14.chocolateChartsPartOne(2018).toIntArray()).containsExactly(5, 9, 4, 1, 4, 2, 9, 8, 8, 2);

        assertThat(Day14.chocolateChartsPartTwo(51589)).isEqualTo(9);
        // assertThat(chocolateChartsPartTwo(01245)).isEqualTo(9);
        assertThat(Day14.chocolateChartsPartTwo(92510)).isEqualTo(18);
        assertThat(Day14.chocolateChartsPartTwo(59414)).isEqualTo(2018);
    }

    @Test
    void inputPartOne() throws Exception {
        assertThat(Day14.chocolateChartsPartOne(846021).toIntArray()).containsExactly(5, 4, 8, 2, 3, 2, 6, 1, 1, 9);
    }

    @Test
    void inputPartTwo() throws Exception {
        assertThat(Day14.chocolateChartsPartTwo(846021)).isEqualTo(20368140);
    }
}
