package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    @Test
    void inputExample() {
        String input = """
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day12.partOne(scanner)).isEqualTo(31);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day12.partTwo(scanner)).isEqualTo(29);
        }

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day12Test.class.getResourceAsStream("/2022/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day12.partOne(scanner)).isEqualTo(517L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day12Test.class.getResourceAsStream("/2022/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day12.partTwo(scanner)).isEqualTo(512L);
        }
    }
}
