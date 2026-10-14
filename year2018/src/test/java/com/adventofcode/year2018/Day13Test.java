package com.adventofcode.year2018;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 13)
class Day13Test {
    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void inputExamplePartOne() {
        String input = """
                /->-\\       \s
                |   |  /----\\
                | /-+--+-\\  |
                | | |  | v  |
                \\-+-/  \\-+--/
                  \\------/  \s""";
        Scanner scanner = new Scanner(input);
        assertThat(Day13.findCrashPosition(scanner)).isEqualTo(Point2D.of(7, 3));
    }

    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void inputExamplePartTwo() {
        String input = """
                />-<\\ \s
                |   | \s
                | /<+-\\
                | | | v
                \\>+</ |
                  |   ^
                  \\<->/""";
        Scanner scanner = new Scanner(input);

        assertThat(Day13.findLastCart(scanner)).isEqualTo(Point2D.of(6, 4));
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day13.findCrashPosition(scanner)).isEqualTo(Point2D.of(100, 21));
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day13.findLastCart(scanner)).isEqualTo(Point2D.of(113, 109));
    }
}
