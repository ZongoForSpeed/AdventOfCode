package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.Scanner;

@AdventOfCode(year = 2018, day = 21)
class Day21Test {
    @Test
    void partOne(Scanner scanner) {
        int result = Day21.PartOne.run(scanner);
        Assertions.assertThat(result).isEqualTo(16134795);
    }

    @Test
    void partTwo(Scanner scanner) {
        int result = Day21.PartTwo.run(scanner);
        Assertions.assertThat(result).isEqualTo(14254292);
    }
}
