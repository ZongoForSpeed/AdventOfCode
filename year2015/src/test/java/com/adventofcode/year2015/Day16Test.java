package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 16)
class Day16Test {
    @Test
    void partOne(Scanner scanner) {
        assertThat(Day16.findAuntSuePartOne(scanner)).isEqualTo(103);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day16.findAuntSuePartTwo(scanner)).isEqualTo(405);
    }

}
