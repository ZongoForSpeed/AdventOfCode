package com.adventofcode.year2017;

import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day17Test {

    @Test
    void inputExample() {
        Day17.CircularBuffer<Integer> circularBuffer = Day17.buildBuffer(9, 3);
        assertThat(circularBuffer).hasToString("0 (9) 5  7  2  4  3  8  6  1");

        IntList circularBuffer2017 = Day17.buildBufferPartOne(2017, 3);
        int index = circularBuffer2017.indexOf(2017);

        assertThat(circularBuffer2017.getInt(index + 1)).isEqualTo(638);

    }

    @Test
    void inputPartOne() {
        IntList circularBuffer = Day17.buildBufferPartOne(2017, 303);
        int index = circularBuffer.indexOf(2017);

        assertThat(circularBuffer.getInt(index + 1)).isEqualTo(1971);
    }


    @Test
    void inputPartTwo() {
        assertThat(Day17.buildBufferPartTwo(50000000, 303)).isEqualTo(17202899);
    }

}
