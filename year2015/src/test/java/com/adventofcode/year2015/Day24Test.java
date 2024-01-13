package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day24Test {

    @Test
    void inputExample() {
        String input = """
                1
                2
                3
                4
                5
                7
                8
                9
                10
                11""";

        assertThat(Day24.quantumEntanglementPartOne(new Scanner(input))).isEqualTo(99);
        assertThat(Day24.quantumEntanglementPartTwo(new Scanner(input))).isEqualTo(44);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2015/day/24/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day24.quantumEntanglementPartOne(scanner)).isEqualTo(11846773891L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2015/day/24/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day24.quantumEntanglementPartTwo(scanner)).isEqualTo(80393059);
        }
    }
}
