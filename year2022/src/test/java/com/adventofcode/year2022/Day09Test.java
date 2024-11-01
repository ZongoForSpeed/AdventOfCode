package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day09Test {

    @Test
    void inputExample1() {
        String input = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2""";

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartOne.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(13);
        }

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(1);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20""";

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(36);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day09Test.class.getResourceAsStream("/2022/day/09/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int size = Day09.PartOne.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(5858);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day09Test.class.getResourceAsStream("/2022/day/09/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(2602);
        }
    }

}
