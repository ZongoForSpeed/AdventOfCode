package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 25)
class Day25Test {

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day25.findClockSignal(scanner)).isEqualTo(189);
    }

    @Test
    void partTwo(Scanner scanner) {
        // No-Op
    }
}
