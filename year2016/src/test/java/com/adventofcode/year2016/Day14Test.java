package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day14Test {

    @Test
    void inputExample() {
        assertThat(Day14.stretchMD5("abc0")).isEqualTo("a107ff634856bb300138cac6568c0f24");
        assertThat(Day14.findKeyPartOne("abc")).isEqualTo(22728);
        assertThat(Day14.findKeyPartTwo("abc")).isEqualTo(22551);
    }

    @Test
    void inputPartOne() {
        assertThat(Day14.findKeyPartOne("ihaygndm")).isEqualTo(15035);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day14.findKeyPartTwo("ihaygndm")).isEqualTo(19968);
    }

}
