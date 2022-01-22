package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test {

    @Test
    void inputExample() {
        String input = """
                swap position 4 with position 0
                swap letter d with letter b
                reverse positions 0 through 4
                rotate left 1 steps
                move position 1 to position 4
                move position 3 to position 0
                rotate based on position of letter b
                rotate based on position of letter d""";

        assertThat(Day21.scramblingFunction(new Scanner(input), "abcde")).isEqualTo("decab");
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2016/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day21.scramblingFunction(scanner, "abcdefgh")).isEqualTo("gcedfahb");

        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2016/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day21.unscramblingFunction(scanner, "fbgdceah")).isEqualTo("hegbdcfa");

        }
    }
}
