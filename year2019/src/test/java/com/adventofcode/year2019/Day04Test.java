package com.adventofcode.year2019;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    @Test
    void testInput() {
        long total = IntStream.range(153517, 630395).filter(Day04::matchPartOne).count();
        assertThat(total).isEqualTo(1729);
    }

    @Test
    void test111111() {
        assertThat(Day04.matchPartOne(111111)).isTrue();
    }

    @Test
    void test223450() {
        assertThat(Day04.matchPartOne(223450)).isFalse();
    }

    @Test
    void test123789() {
        assertThat(Day04.matchPartOne(123789)).isFalse();
    }

    @Test
    void testInput2() {
        long total = IntStream.range(153517, 630395).filter(Day04::matchPartTwo).count();
        assertThat(total).isEqualTo(1172);
    }

    @Test
    void test112233() {
        assertThat(Day04.matchPartTwo(112233)).isTrue();
    }

    @Test
    void test123444() {
        assertThat(Day04.matchPartTwo(123444)).isFalse();
    }

    @Test
    void test111122() {
        assertThat(Day04.matchPartTwo(111122)).isTrue();
    }

}
