package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 4)
class Day04Test {

    @Test
    void inputExample1() {
        var input = """
                ..X...
                .SAMX.
                .A..A.
                XMAS.S
                .X....""";

        try (var scanner = new Scanner(input)) {
            int count = Day04.xmasPartOne(scanner);
            assertThat(count).isEqualTo(4);
        }

    }

    @Test
    void inputExample2() {
        var input = """
                MMMSXXMASM
                MSAMXMSMSA
                AMXSXMAAMM
                MSAMASMSMX
                XMASAMXAMM
                XXAMMXXAMA
                SMSMSASXSS
                SAXAMASAAA
                MAMMMXMMMM
                MXMXAXMASX""";

        try (var scanner = new Scanner(input)) {
            int count = Day04.xmasPartOne(scanner);
            assertThat(count).isEqualTo(18);
        }

        try (var scanner = new Scanner(input)) {
            int count = Day04.xmasPartTwo(scanner);
            assertThat(count).isEqualTo(9);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day04.xmasPartOne(scanner)).isEqualTo(2575);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day04.xmasPartTwo(scanner)).isEqualTo(2041);
    }

}
