package com.adventofcode.year2018;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


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
    void inputPartOne() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2018/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.findCrashPosition(scanner)).isEqualTo(Point2D.of(100, 21));
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2018/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.findLastCart(scanner)).isEqualTo(Point2D.of(113, 109));
        }
    }
}
