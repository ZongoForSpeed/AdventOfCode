package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day09Test {

    @Test
    void inputExample() {
        assertThat(Day09.playMarblesMania(9, 25)).hasValue(32);
        assertThat(Day09.playMarblesMania(10, 1618)).hasValue(8317);
        assertThat(Day09.playMarblesMania(13, 7999)).hasValue(146373);
        assertThat(Day09.playMarblesMania(17, 1104)).hasValue(2764);
        assertThat(Day09.playMarblesMania(21, 6111)).hasValue(54718);
        assertThat(Day09.playMarblesMania(30, 5807)).hasValue(37305);
    }

    @Test
    void inputPartOne() throws IOException {
        // 464 players; last marble is worth 71730 points
        assertThat(Day09.playMarblesMania(464, 71730)).hasValue(380705);
    }

    @Test
    void inputPartTwo() throws IOException {
        assertThat(Day09.playMarblesMania(464, 71730 * 100)).hasValue(3171801582L);
    }

}
