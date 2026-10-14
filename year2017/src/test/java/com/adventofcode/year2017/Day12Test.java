package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 12)
class Day12Test {
    @Test
    void inputExample() {
        var input = """
                0 <-> 2
                1 <-> 1
                2 <-> 0, 3, 4
                3 <-> 2, 4
                4 <-> 2, 3, 6
                5 <-> 6
                6 <-> 4, 5""";

        assertThat(Day12.digitalPlumberPartOne(new Scanner(input))).hasSize(6).contains(0, 2, 6, 4, 3, 5);
        assertThat(Day12.digitalPlumberPartTwo(new Scanner(input))).hasSize(2).contains(0, 1);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day12.digitalPlumberPartOne(scanner)).hasSize(130);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day12.digitalPlumberPartTwo(scanner)).hasSize(189);
    }
}
