package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test extends AbstractTest {

    protected Day04Test() {
        super(2024, 4);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day04.xmasPartOne(scanner)).isEqualTo(2575);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day04.xmasPartTwo(scanner)).isEqualTo(2041);
    }

}
