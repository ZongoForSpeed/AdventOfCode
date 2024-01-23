package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day04Test {

    @Test
    void inputExample() {
        String input = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8""";

        {
            Scanner scanner = new Scanner(input);
            long count = Day04.PartOne.countFullyContained(scanner);
            Assertions.assertThat(count).isEqualTo(2);
        }

        {
            Scanner scanner = new Scanner(input);
            long count = Day04.PartTwo.countOverlaps(scanner);
            Assertions.assertThat(count).isEqualTo(4);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day04Test.class.getResourceAsStream("/2022/day/04/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long count = Day04.PartOne.countFullyContained(scanner);
            Assertions.assertThat(count).isEqualTo(444);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day04Test.class.getResourceAsStream("/2022/day/04/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long count = Day04.PartTwo.countOverlaps(scanner);
            Assertions.assertThat(count).isEqualTo(801);
        }
    }


}
