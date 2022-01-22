package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2015/day/16/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.findAuntSuePartOne(scanner)).isEqualTo(103);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2015/day/16/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.findAuntSuePartTwo(scanner)).isEqualTo(405);
        }
    }

}
