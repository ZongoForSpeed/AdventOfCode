package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 17)
class Day17Test {
    @Test
    void inputExample() {
        var input = """
                20
                15
                10
                5
                5""";

        assertThat(Day17.findCombinationsPartOne(new Scanner(input), 25)).isEqualTo(4);
        assertThat(Day17.findCombinationsPartTwo(new Scanner(input), 25)).isEqualTo(3);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day17.findCombinationsPartOne(scanner, 150)).isEqualTo(4372);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day17.findCombinationsPartTwo(scanner, 150)).isEqualTo(4);
    }
}
