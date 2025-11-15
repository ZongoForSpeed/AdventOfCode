package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day01Test extends AbstractTest {

    protected Day01Test() {
        super(2023, 1);
    }

    @Test
    void examplePartOne() {
        String input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        Scanner scanner = new Scanner(input);
        Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(142);
    }

    @Test
    void examplePartTwo() {
        String input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        Scanner scanner = new Scanner(input);
        Assertions.assertThat(Day01.PartTwo.sumCalibrationValues(scanner)).isEqualTo(281);
    }

    @Override
    public void partOne(Scanner scanner) {
        Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(55712);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Assertions.assertThat(Day01.PartTwo.sumCalibrationValues(scanner)).isEqualTo(55413);
    }
}
