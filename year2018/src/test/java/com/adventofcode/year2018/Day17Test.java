package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void example() {
        String input = """
                x=495, y=2..7
                y=7, x=495..501
                x=501, y=3..7
                x=498, y=2..4
                x=506, y=1..2
                x=498, y=10..13
                x=504, y=10..13
                y=13, x=498..504""";
        Day17 day17 = new Day17(new Scanner(input));
        assertThat(day17.partOne()).isEqualTo(57);
        assertThat(day17.partTwo()).isEqualTo(29);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {

            Day17 day17 = new Day17(scanner);
            assertThat(day17.partOne()).isEqualTo(31861);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {

            Day17 day17 = new Day17(scanner);
            assertThat(day17.partTwo()).isEqualTo(26030);
        }
    }

}
