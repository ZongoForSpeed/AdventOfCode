package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day09Test {


    @Test
    void inputExample() {
        String input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

        {
            Scanner scanner = new Scanner(input);
            int sum = Day09.PartOne.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(114);
        }

        {
            Scanner scanner = new Scanner(input);
            int sum = Day09.PartTwo.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(2);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day09Test.class.getResourceAsStream("/2023/day/09/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int sum = Day09.PartOne.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(1819125966);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day09Test.class.getResourceAsStream("/2023/day/09/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int sum = Day09.PartTwo.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(1140);
        }
    }
}
