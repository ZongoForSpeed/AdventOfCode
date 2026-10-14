package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 3)
public class Day03Test {

    @Test
    void inputExample() {
        var input = """
                987654321111111
                811111111111119
                234234234234278
                818181911112111""";

        try (var scanner = new Scanner(input)) {
            long sum = Day03.joltageLobby(scanner, 2);
            assertThat(sum).isEqualTo(357);
        }

        try (var scanner = new Scanner(input)) {
            long sum = Day03.joltageLobby(scanner, 12);
            assertThat(sum).isEqualTo(3121910778619L);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {

        long sum = Day03.joltageLobby(scanner, 2);
        assertThat(sum).isEqualTo(17316);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {

        long sum = Day03.joltageLobby(scanner, 12);
        assertThat(sum).isEqualTo(171741365473332L);
    }
}
