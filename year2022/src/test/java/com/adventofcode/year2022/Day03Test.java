package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day03Test {

    @Test
    void inputExample() {
        String input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw""";

        {
            Scanner scanner = new Scanner(input);
            int sum = Day03.PartOne.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(157);
        }

        {
            Scanner scanner = new Scanner(input);
            int sum = Day03.PartTwo.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(70);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2022/day/03/input")) {
            try (Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
                int priority = Day03.PartOne.sumPriorities(scanner);
                Assertions.assertThat(priority).isEqualTo(8298);
            }
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2022/day/03/input")) {
            try (Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
                int priority = Day03.PartTwo.sumPriorities(scanner);
                Assertions.assertThat(priority).isEqualTo(2708);
            }
        }
    }

}
