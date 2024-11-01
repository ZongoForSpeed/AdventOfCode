package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day02Test {

    @Test
    void inputExample() {
        String input = """
                A Y
                B X
                C Z""";

        {
            Scanner scanner = new Scanner(input);
            int score = Day02.PartOne.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(15);
        }

        {
            Scanner scanner = new Scanner(input);
            int score = Day02.PartTwo.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(12);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day02Test.class.getResourceAsStream("/2022/day/02/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int score = Day02.PartOne.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(15691);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day02Test.class.getResourceAsStream("/2022/day/02/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int score = Day02.PartTwo.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(12989);
        }
    }


}
