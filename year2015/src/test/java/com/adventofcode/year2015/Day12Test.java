package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 12)
class Day12Test {
    @Test
    void partOne(Scanner scanner) {
        assertThat(Day12.computeSumPartOne(scanner)).isEqualTo(156366);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day12.computeSumPartTwo(scanner)).isEqualTo(96852);
    }
}
