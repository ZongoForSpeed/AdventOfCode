package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test {

    @Test
    void inputExample() {
        String input = """
                ULL
                RRDDD
                LURDL
                UUUUD""";

        assertThat(Day02.findCodePartOne(new Scanner(input))).isEqualTo("1985");
        assertThat(Day02.findCodePartTwo(new Scanner(input))).isEqualTo("5DB3");
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2016/day/2/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day02.findCodePartOne(scanner)).isEqualTo("97289");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2016/day/2/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day02.findCodePartTwo(scanner)).isEqualTo("9A7DC");
        }
    }

}
