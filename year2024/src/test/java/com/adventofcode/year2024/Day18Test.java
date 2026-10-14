package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 18)
class Day18Test {

    @Test
    void inputExample() {
        var input = """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0""";

        try (var scanner = new Scanner(input)) {
            long path = Day18.partOne(scanner, 6, 12);
            assertThat(path).isEqualTo(22);
        }

        try (var scanner = new Scanner(input)) {
            String memory = Day18.partTwo(scanner, 6);
            assertThat(memory).isEqualTo("6,1");
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long path = Day18.partOne(scanner, 70, 1024);
        assertThat(path).isEqualTo(438L);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        String memory = Day18.partTwo(scanner, 70);
        assertThat(memory).isEqualTo("26,22");
    }

}
