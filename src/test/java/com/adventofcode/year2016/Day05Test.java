package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test {

    @Test
    void inputExample() {
        assertThat(Day05.findPasswordPartOne("abc")).isEqualTo("18f47a30");
        assertThat(Day05.findPasswordPartTwo("abc")).isEqualTo("05ace8e3");
    }

    @Test
    void inputPartOne() {
        assertThat(Day05.findPasswordPartOne("cxdnnyjw")).isEqualTo("f77a0e6e");
    }

    @Test
    void inputPartTwo() {
        assertThat(Day05.findPasswordPartTwo("cxdnnyjw")).isEqualTo("999828ec");
    }

}
