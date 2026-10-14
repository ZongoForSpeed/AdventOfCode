package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 4)
public class Day04Test {


    @Test
    void inputExample() {
        var input = """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.""";

        try (var scanner = new Scanner(input)) {
            int size = Day04.partOne(scanner);
            assertThat(size).isEqualTo(13);
        }

        try (var scanner = new Scanner(input)) {
            int size = Day04.partTwo(scanner);
            assertThat(size).isEqualTo(43);
        }

    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int size = Day04.partOne(scanner);
        assertThat(size).isEqualTo(1419);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        int size = Day04.partTwo(scanner);
        assertThat(size).isEqualTo(8739);
    }
}
