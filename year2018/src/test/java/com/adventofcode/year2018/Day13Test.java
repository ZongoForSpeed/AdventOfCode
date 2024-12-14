package com.adventofcode.year2018;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test extends AbstractTest {
    Day13Test() {
        super(2018, 13);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day13.findCrashPosition(scanner)).isEqualTo(Point2D.of(100, 21));
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day13.findLastCart(scanner)).isEqualTo(Point2D.of(113, 109));
    }
}
