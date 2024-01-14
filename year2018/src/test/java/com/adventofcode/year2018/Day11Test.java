package com.adventofcode.year2018;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.Point3D;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day11Test {

    @Test
    void testExample() {
        assertThat(Day11.powerLevel(122, 79, 57)).isEqualTo(-5);
        assertThat(Day11.powerLevel(217, 196, 39)).isZero();
        assertThat(Day11.powerLevel(101, 153, 71)).isEqualTo(4);

        assertThat(Day11.findLargestPartOne(18, 3)).isEqualTo(Point2D.of(33, 45));
        assertThat(Day11.findLargestPartOne(42, 3)).isEqualTo(Point2D.of(21, 61));

        assertThat(Day11.findLargestPartTwo(18)).isEqualTo(Point3D.of(90, 269, 16));
        assertThat(Day11.findLargestPartTwo(42)).isEqualTo(Point3D.of(232, 251, 12));
    }

    @Test
    void inputPartOne() throws IOException {
        assertThat(Day11.findLargestPartOne(7672, 3)).isEqualTo(Point2D.of(22, 18));
    }

    @Test
    void inputPartTwo() throws IOException {
        assertThat(Day11.findLargestPartTwo(7672)).isEqualTo(Point3D.of(234, 197, 14));
    }

}
