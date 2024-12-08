package com.adventofcode.year2024;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    @Test
    void inputExample() {
        String input = """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............""";

        try (Scanner scanner = new Scanner(input)) {
            Set<Point2D> antinodes = Day08.partOne(scanner);
            assertThat(antinodes).hasSize(14);
        }

        try (Scanner scanner = new Scanner(input)) {
            Set<Point2D> antinodes = Day08.partTwo(scanner);
            assertThat(antinodes).hasSize(34);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2024/day/08/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day08.partOne(scanner)).hasSize(394);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2024/day/08/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day08.partTwo(scanner)).hasSize(1277);
        }
    }


}
