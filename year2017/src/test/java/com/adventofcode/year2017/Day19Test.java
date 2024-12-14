package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day19Test extends AbstractTest {
    Day19Test() {
        super(2017, 19);
    }

    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void inputExample() {
        String input = """
                     |         \s
                     |  +--+   \s
                     A  |  C   \s
                 F---|----E|--+\s
                     |  |  |  D\s
                     +B-+  +--+\s
                """;

        assertThat(Day19.findPacketsPartOne(new Scanner(input))).isEqualTo("ABCDEF");
        assertThat(Day19.findPacketsPartTwo(new Scanner(input))).isEqualTo(38);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day19.findPacketsPartOne(scanner)).isEqualTo("XYFDJNRCQA");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day19.findPacketsPartTwo(scanner)).isEqualTo(17450);
    }
}
