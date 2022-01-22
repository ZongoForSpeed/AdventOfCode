package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test {

    @Test
    void inputExample() throws IOException {
        String input = """
                Disc #1 has 5 positions; at time=0, it is at position 4.
                Disc #2 has 2 positions; at time=0, it is at position 1.""";

        Scanner scanner = new Scanner(input);
        assertThat(Day15.findTimePartOne(scanner)).isEqualTo(5);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2016/day/15/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {

            assertThat(Day15.findTimePartOne(scanner)).isEqualTo(122318);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2016/day/15/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day15.findTimePartTwo(scanner)).isEqualTo(3208583);
        }
    }

}
