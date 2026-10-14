package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 24)
class Day24Test {

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day24.aluPartOne(scanner)).isEqualTo(39924989499969L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day24.aluPartTwo(scanner)).isEqualTo(16811412161117L);
    }

}
