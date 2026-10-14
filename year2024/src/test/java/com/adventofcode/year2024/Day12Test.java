package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 12)
class Day12Test {

    @Test
    void inputExample1() {
        var input = """
                AAAA
                BBCD
                BBCC
                EEEC""";

        try (var scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(140);
        }

        try (var scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(80);
        }
    }

    @Test
    void inputExample2() {
        var input = """
                OOOOO
                OXOXO
                OOOOO
                OXOXO
                OOOOO""";

        try (var scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(772);
        }


        try (var scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(436);
        }
    }

    @Test
    void inputExample3() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(1930);
        }


        try (var scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(1206);
        }
    }

    @Test
    void inputExample4() {
        var input = """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE""";

        try (var scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(236);
        }
    }

    @Test
    void inputExample5() {
        var input = """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA""";

        try (var scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(368);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day12.partOne(scanner)).isEqualTo(1533644L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day12.partTwo(scanner)).isEqualTo(936718L);
    }

}
