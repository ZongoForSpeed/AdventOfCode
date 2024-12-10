package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    @Test
    void inputExample1() {
        String input = """
                0123
                1234
                8765
                9876""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partOne(scanner);
            assertThat(score).isEqualTo(1);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                ...0...
                ...1...
                ...2...
                6543456
                7.....7
                8.....8
                9.....9""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partOne(scanner);
            assertThat(score).isEqualTo(2);
        }
    }

    @Test
    void inputExample3() {
        String input = """
                ..90..9
                ...1.98
                ...2..7
                6543456
                765.987
                876....
                987....""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partOne(scanner);
            assertThat(score).isEqualTo(4);
        }

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partTwo(scanner);
            assertThat(score).isEqualTo(13);
        }
    }

    @Test
    void inputExample4() {
        String input = """
                10..9..
                2...8..
                3...7..
                4567654
                ...8..3
                ...9..2
                .....01""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partOne(scanner);
            assertThat(score).isEqualTo(3);
        }
    }

    @Test
    void inputExample5() {
        String input = """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partOne(scanner);
            assertThat(score).isEqualTo(36);
        }

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partTwo(scanner);
            assertThat(score).isEqualTo(81);
        }
    }

    @Test
    void inputExample6() {
        String input = """
                .....0.
                ..4321.
                ..5..2.
                ..6543.
                ..7..4.
                ..8765.
                ..9....""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partTwo(scanner);
            assertThat(score).isEqualTo(3);
        }
    }

    @Test
    void inputExample7() {
        String input = """
                012345
                123456
                234567
                345678
                4.6789
                56789.""";

        try (Scanner scanner = new Scanner(input)) {
            int score = Day10.partTwo(scanner);
            assertThat(score).isEqualTo(227);
        }
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2024/day/10/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day10.partOne(scanner)).isEqualTo(611);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2024/day/10/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day10.partTwo(scanner)).isEqualTo(1380);
        }
    }


}
