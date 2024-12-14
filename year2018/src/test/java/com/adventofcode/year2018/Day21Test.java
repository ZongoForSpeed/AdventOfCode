package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;

import java.util.Scanner;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2018, 21);
    }

    @Override
    public void partOne(Scanner scanner) {
        int result = Day21.PartOne.run(scanner);
        Assertions.assertThat(result).isEqualTo(16134795);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int result = Day21.PartTwo.run(scanner);
        Assertions.assertThat(result).isEqualTo(14254292);
    }
}
