package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 1)
class Day01Test {
    @Test
    void partOne(Scanner scanner) {
        long frequency = Day01.addFrequencies(scanner);
        assertThat(frequency).isEqualTo(508);
    }

    @Test
    void partTwo(Scanner scanner) {
        long frequency = Day01.findDuplicateFrequency(scanner);
        assertThat(frequency).isEqualTo(549);
    }

}
