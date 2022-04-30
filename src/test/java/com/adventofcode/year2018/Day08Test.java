package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test {

    @Test
    void inputExample() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        assertThat(Day08.readNode(new Scanner(input))).isEqualTo(138);
        assertThat(Day08.rootNode(new Scanner(input))).isEqualTo(66);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2018/day/8/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day08.readNode(scanner)).isEqualTo(47112);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2018/day/8/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day08.rootNode(scanner)).isEqualTo(28237);
        }
    }

}
