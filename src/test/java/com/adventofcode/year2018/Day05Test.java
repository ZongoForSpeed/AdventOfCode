package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test {

    @Test
    void inputExample() {
        String input = "dabAcCaCBAcCcaDA";

        assertThat(Day05.getReductionSize(input, ignore -> true)).isEqualTo(10);

        int minSize = Day05.minReductionSize(input);

        assertThat(minSize).isEqualTo(4);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2018/day/5/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.getReductionSize(scanner.nextLine(), ignore -> true)).isEqualTo(11310);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2018/day/5/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.minReductionSize(scanner.nextLine())).isEqualTo(6020);
        }
    }

}
