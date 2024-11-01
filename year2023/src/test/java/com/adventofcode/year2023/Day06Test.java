package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day06Test {

    @Test
    void examplePartOne() {
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        Scanner scanner = new Scanner(input);

        long reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(288);
    }

    @Test
    void examplePartTwo() {
        String input = """
                Time:      71530
                Distance:  940200""";

        Scanner scanner = new Scanner(input);

        long reduce = Day06.PartTwo.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(71503);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day06Test.class.getResourceAsStream("/2023/day/06/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long reduce = Day06.PartOne.raceRecord(scanner);
            Assertions.assertThat(reduce).isEqualTo(131376);
        }
    }

    @Test
    void inputPartTwo() {
        String input = """
                Time:       51699878
                Distance:   377117112241505
                """;

        Scanner scanner = new Scanner(input);

        long reduce = Day06.PartTwo.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(34123437L);
    }

}
