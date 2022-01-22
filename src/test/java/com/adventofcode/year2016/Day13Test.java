package com.adventofcode.year2016;

import com.adventofcode.map.Point2D;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test {

    @Test
    void inputExample() throws IOException {
        assertThat(Day13.findPath(10, Point2D.of(1, 1), Point2D.of(7, 4))).isEqualTo(11);
    }

    @Test
    void inputPartOne() throws IOException {
        long path = Day13.findPath(1364, Point2D.of(1, 1), Point2D.of(31, 39));
        assertThat(path).isEqualTo(86);
    }

    @Test
    void inputPartTwo() throws IOException {
        int favoriteNumber = 1364;
        Point2D start = Point2D.of(1, 1);

        long path = Day13.algorithm(favoriteNumber, start, 50);
        assertThat(path).isEqualTo(127);
    }

}
