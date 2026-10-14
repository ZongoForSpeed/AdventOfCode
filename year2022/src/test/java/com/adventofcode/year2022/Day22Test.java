package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 22)
class Day22Test {

    @Test
    void example() {
        var input = """
                        ...#
                        .#..
                        #...
                        ....
                ...#.......#
                ........#...
                ..#....#....
                ..........#.
                        ...#....
                        .....#..
                        .#......
                        ......#.
                
                10R5L5R10L4R5L5""";

        try (var scanner = new Scanner(input)) {
            int password = Day22.partOne(scanner);
            assertThat(password).isEqualTo(6032);
        }

        try (var scanner = new Scanner(input)) {
            int password = Day22.partTwoExample(scanner);
            assertThat(password).isEqualTo(5031);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int monkeyMap = Day22.partOne(scanner);
        assertThat(monkeyMap).isEqualTo(117102);
    }

    @Test
    void partTwo(Scanner scanner) {
        int monkeyCube = Day22.partTwo(scanner);
        assertThat(monkeyCube).isEqualTo(135297);
    }

}
