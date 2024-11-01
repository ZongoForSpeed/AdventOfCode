package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day13Test {

    @Test
    void inputExample() {
        String input = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                                
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#""";

        {
            Scanner scanner = new Scanner(input);

            long total = Day13.PartOne.findReflection(scanner);

            Assertions.assertThat(total).isEqualTo(405);
        }

        {
            Scanner scanner = new Scanner(input);

            long total = Day13.PartTwo.findSmudge(scanner);

            Assertions.assertThat(total).isEqualTo(400);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day13Test.class.getResourceAsStream("/2023/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long reflection = Day13.PartOne.findReflection(scanner);
            Assertions.assertThat(reflection).isEqualTo(34993);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day13Test.class.getResourceAsStream("/2023/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long reflection = Day13.PartTwo.findSmudge(scanner);
            Assertions.assertThat(reflection).isEqualTo(29341);
        }
    }
}
