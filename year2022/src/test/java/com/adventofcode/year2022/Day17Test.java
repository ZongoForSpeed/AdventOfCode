package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 17)
class Day17Test {
    @Test
    void example() {
        var input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

        assertThat(Day17.pyroclasticFlow(input, 10, true)).isEqualTo(17);

        assertThat(Day17.pyroclasticFlow(input, 2022, false)).isEqualTo(3068);
        assertThat(Day17.pyroclasticFlow(input, 1_000_000_000_000L, false)).isEqualTo(1514285714288L);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 2022, false)).isEqualTo(3227);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 1_000_000_000_000L, false)).isEqualTo(1597714285698L);
    }

}
