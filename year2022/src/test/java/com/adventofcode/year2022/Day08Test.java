package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day08Test {

    @Test
    void inputExample() {
        String input = """
                30373
                25512
                65332
                33549
                35390""";

        {
            Scanner scanner = new Scanner(input);
            long cardinality = Day08.PartOne.countVisibleTrees(scanner);
            Assertions.assertThat(cardinality).isEqualTo(21);
        }

        {
            Scanner scanner = new Scanner(input);
            int maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
            Assertions.assertThat(maxScenicScore).isEqualTo(8);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day08Test.class.getResourceAsStream("/2022/day/08/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long cardinality = Day08.PartOne.countVisibleTrees(scanner);
            Assertions.assertThat(cardinality).isEqualTo(1820);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day08Test.class.getResourceAsStream("/2022/day/08/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
            Assertions.assertThat(maxScenicScore).isEqualTo(385112);
        }
    }
}
