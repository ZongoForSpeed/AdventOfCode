package com.adventofcode.year2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

    @Test
    void testCrabCups() {
        String input = "389125467";
        assertThat(Day23.crabCupsPart1(input, 10)).isEqualTo("92658374");
        assertThat(Day23.crabCupsPart1(input, 100)).isEqualTo("67384529");

        assertThat(Day23.crabCupsPart2(input, 10_000_000, 1_000_000)).isEqualTo(149245887792L);
    }

    @Test
    void inputCrabCups() {
        String input = "789465123";
        assertThat(Day23.crabCupsPart1(input, 100)).isEqualTo("98752463");

        assertThat(Day23.crabCupsPart2(input, 10_000_000, 1_000_000)).isEqualTo(2000455861L);
    }

}
