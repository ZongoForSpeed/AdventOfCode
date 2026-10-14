package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 8)
class Day08Test {

    @Test
    void inputExample() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            var antinodes = Day08.partOne(scanner);
            assertThat(antinodes).hasSize(14);
        }

        try (var scanner = new Scanner(input)) {
            var antinodes = Day08.partTwo(scanner);
            assertThat(antinodes).hasSize(34);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day08.partOne(scanner)).hasSize(394);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day08.partTwo(scanner)).hasSize(1277);
    }

}
