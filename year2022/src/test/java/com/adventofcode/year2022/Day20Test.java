package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
    void inputExample() {
        String input = """
                1
                2
                -3
                3
                -2
                0
                4""";

        {
            Scanner scanner = new Scanner(input);
            long decrypt = Day20.decryptPartOne(scanner);
            assertThat(decrypt).isEqualTo(3);
        }

        {
            Scanner scanner = new Scanner(input);
            long decrypt = Day20.decryptPartTwo(scanner);
            assertThat(decrypt).isEqualTo(1623178306L);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day20.decryptPartOne(scanner)).isEqualTo(23321);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day20.decryptPartTwo(scanner)).isEqualTo(1428396909280L);
        }
    }

}
