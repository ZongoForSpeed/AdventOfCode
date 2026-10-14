package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 1)
public class Day01Test {

    @Test
    void inputExample() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";

        try (var scanner = new Scanner(input)) {
            int password = Day01.countPasswordPartOne(scanner);

            assertThat(password).isEqualTo(3);
        }

        try (var scanner = new Scanner(input)) {
            int password = Day01.countPasswordPartTwo(scanner);

            assertThat(password).isEqualTo(6);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int password = Day01.countPasswordPartOne(scanner);
        assertThat(password).isEqualTo(1031);
    }


    @Test
    void partTwo(Scanner scanner) throws Exception {
        int password = Day01.countPasswordPartTwo(scanner);
        assertThat(password).isEqualTo(5831);
    }
}
