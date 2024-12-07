package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {

    @Test
    void inputExample() {
        String input = """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...""";

        try (Scanner scanner = new Scanner(input)) {
            int path = Day06.partOne(scanner);
            assertThat(path).isEqualTo(41);
        }

        try (Scanner scanner = new Scanner(input)) {
            int count = Day06.partTwo(scanner);
            assertThat(count).isEqualTo(6);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day06Test.class.getResourceAsStream("/2024/day/06/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day06.partOne(scanner)).isEqualTo(4752);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day06Test.class.getResourceAsStream("/2024/day/06/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day06.partTwo(scanner)).isEqualTo(1719);
        }
    }


}
