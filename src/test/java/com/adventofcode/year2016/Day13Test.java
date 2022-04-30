package com.adventofcode.year2016;

import com.adventofcode.point.Point2D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test {

    @Test
    void inputExample() {
        assertThat(Day13.findPath(10, Point2D.of(1, 1), Point2D.of(7, 4))).isEqualTo(11);
    }

    @Test
    void inputPartOne() {
        long path = Day13.findPath(1364, Point2D.of(1, 1), Point2D.of(31, 39));
        assertThat(path).isEqualTo(86);
    }

    @Test
    void inputPartTwo() {
        int favoriteNumber = 1364;
        Point2D start = Point2D.of(1, 1);

        long path = Day13.algorithm(favoriteNumber, start, 50);
        assertThat(path).isEqualTo(127);
    }

}
