package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 6)
public class Day06Test {

    @Test
    void inputExample() {
        var input = """
                123 328  51 64 \s
                 45 64  387 23 \s
                  6 98  215 314\s
                *   +   *   +  \s""";

        try (var scanner = new Scanner(input)) {
            long result = Day06.partOne(scanner);
            assertThat(result).isEqualTo(4277556);
        }

        try (var scanner = new Scanner(input)) {
            long result = Day06.partTwo(scanner);
            assertThat(result).isEqualTo(3263827);
        }

    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long result = Day06.partOne(scanner);
        assertThat(result).isEqualTo(5667835681547L);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long result = Day06.partTwo(scanner);
        assertThat(result).isEqualTo(9434900032651L);
    }

}
