package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2015/day/12/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.computeSumPartOne(scanner)).isEqualTo(156366);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2015/day/12/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.computeSumPartTwo(scanner)).isEqualTo(96852);
        }
    }
}
