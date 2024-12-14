package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test extends AbstractTest {

    Day03Test() {
        super(2019, 3);
    }

    @Test
    void testSimplePath1() {
        assertThat(Day03.intersection("R8,U5,L5,D3", "U7,R6,D4,L4")).isEqualTo(6);
    }

    @Test
    void testSimplePath2() {
        assertThat(Day03.intersection("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")).isEqualTo(159);
    }

    @Test
    void testSimplePath3() {
        assertThat(Day03.intersection("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")).isEqualTo(135);
    }

    @Test
    void testSimpleSteps1() {
        assertThat(Day03.intersectionSteps("R8,U5,L5,D3", "U7,R6,D4,L4")).isEqualTo(30);
    }

    @Test
    void testSimpleSteps2() {
        assertThat(Day03.intersectionSteps("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")).isEqualTo(610);
    }

    @Test
    void testSimpleSteps3() {
        assertThat(Day03.intersectionSteps("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")).isEqualTo(410);
    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(Day03.intersection(lines.getFirst(), lines.get(1))).isEqualTo(260);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(Day03.intersectionSteps(lines.getFirst(), lines.get(1))).isEqualTo(15612);
    }
}
