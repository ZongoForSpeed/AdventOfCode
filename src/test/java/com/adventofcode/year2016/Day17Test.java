package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day17Test {

    @Test
    void inputExample() {
        assertThat(Day17.findShortestPath("ihgpwlah")).isEqualTo("DDRRRD");
        assertThat(Day17.findShortestPath("kglvqrro")).isEqualTo("DDUDRLRRUDRD");
        assertThat(Day17.findShortestPath("ulqzkmiv")).isEqualTo("DRURDRUDDLLDLUURRDULRLDUUDDDRR");

        assertThat(Day17.findLongestPath("ihgpwlah")).hasSize(370);
        assertThat(Day17.findLongestPath("kglvqrro")).hasSize(492);
        assertThat(Day17.findLongestPath("ulqzkmiv")).hasSize(830);
    }

    @Test
    void inputPartOne() {
        assertThat(Day17.findShortestPath("ioramepc")).isEqualTo("RDDRULDDRR");
    }

    @Test
    void inputPartTwo() {
        assertThat(Day17.findLongestPath("ioramepc")).hasSize(766);
    }

}
