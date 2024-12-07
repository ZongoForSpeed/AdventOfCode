package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    @Test
    void inputExample1() {
        String input = """
                ..X...
                .SAMX.
                .A..A.
                XMAS.S
                .X....""";

        try (Scanner scanner = new Scanner(input)) {
            int count = Day04.xmasPartOne(scanner);
            assertThat(count).isEqualTo(4);
        }

    }

    @Test
    void inputExample2() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            int count = Day04.xmasPartOne(scanner);
            assertThat(count).isEqualTo(18);
        }

        try (Scanner scanner = new Scanner(input)) {
            int count = Day04.xmasPartTwo(scanner);
            assertThat(count).isEqualTo(9);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day04Test.class.getResourceAsStream("/2024/day/04/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day04.xmasPartOne(scanner)).isEqualTo(2575);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day04Test.class.getResourceAsStream("/2024/day/04/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day04.xmasPartTwo(scanner)).isEqualTo(2041);
        }
    }

}
