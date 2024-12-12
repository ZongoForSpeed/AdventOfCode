package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    @Test
    void inputExample() {
        String input = """
                2,2,2
                1,2,2
                3,2,2
                2,1,2
                2,3,2
                2,2,1
                2,2,3
                2,2,4
                2,2,6
                1,2,5
                3,2,5
                2,1,5
                2,3,5""";

        {
            Scanner scanner = new Scanner(input);
            long surface = Day18.computeSurfaceArea(scanner);
            assertThat(surface).isEqualTo(64);
        }

        {
            Scanner scanner = new Scanner(input);
            long surface = Day18.exteriorSurfaceArea(scanner);
            assertThat(surface).isEqualTo(58);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day18Test.class.getResourceAsStream("/2022/day/18/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day18.computeSurfaceArea(scanner)).isEqualTo(4310);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day18Test.class.getResourceAsStream("/2022/day/18/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day18.exteriorSurfaceArea(scanner)).isEqualTo(2466);
        }
    }

}
