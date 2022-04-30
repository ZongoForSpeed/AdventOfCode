package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2018/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            long frequency = Day01.addFrequencies(scanner);
            assertThat(frequency).isEqualTo(508);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2018/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            long frequency = Day01.findDuplicateFrequency(scanner);
            assertThat(frequency).isEqualTo(549);
        }
    }

}
