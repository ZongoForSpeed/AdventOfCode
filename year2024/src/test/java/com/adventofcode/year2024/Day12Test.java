package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    @Test
    void inputExample1() {
        String input = """
                AAAA
                BBCD
                BBCC
                EEEC""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(140);
        }

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(80);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                OOOOO
                OXOXO
                OOOOO
                OXOXO
                OOOOO""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(772);
        }


        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(436);
        }
    }

    @Test
    void inputExample3() {
        String input = """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(1930);
        }


        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(1206);
        }
    }

    @Test
    void inputExample4() {
        String input = """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(236);
        }
    }

    @Test
    void inputExample5() {
        String input = """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(368);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2024/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.partOne(scanner)).isEqualTo(1533644L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2024/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.partTwo(scanner)).isEqualTo(936718L);
        }
    }

}
