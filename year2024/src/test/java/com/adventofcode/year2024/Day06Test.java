package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 6)
class Day06Test {

    @Test
    void inputExample() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            int path = Day06.partOne(scanner);
            assertThat(path).isEqualTo(41);
        }

        try (var scanner = new Scanner(input)) {
            int count = Day06.partTwo(scanner);
            assertThat(count).isEqualTo(6);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day06.partOne(scanner)).isEqualTo(4752);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day06.partTwo(scanner)).isEqualTo(1719);
    }

}
