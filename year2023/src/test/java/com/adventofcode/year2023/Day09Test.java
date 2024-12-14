package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day09Test extends AbstractTest {
    Day09Test() {
        super(2023, 9);
    }


    @Test
    void inputExample() {
        String input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

        {
            Scanner scanner = new Scanner(input);
            int sum = Day09.PartOne.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(114);
        }

        {
            Scanner scanner = new Scanner(input);
            int sum = Day09.PartTwo.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(2);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        int sum = Day09.PartOne.extrapolatedValues(scanner);
        Assertions.assertThat(sum).isEqualTo(1819125966);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int sum = Day09.PartTwo.extrapolatedValues(scanner);
        Assertions.assertThat(sum).isEqualTo(1140);
    }
}
