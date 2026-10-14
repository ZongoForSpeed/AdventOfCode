package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 14)
class Day14Test {
    @Test
    void inputExample() {
        var input = """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9""";

        {
            var scanner = new Scanner(input);
            assertThat(Day14.partOne(scanner)).isEqualTo(24);
        }

        {
            var scanner = new Scanner(input);
            assertThat(Day14.partTwo(scanner)).isEqualTo(93);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day14.partOne(scanner)).isEqualTo(795);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day14.partTwo(scanner)).isEqualTo(30214);
    }
}
