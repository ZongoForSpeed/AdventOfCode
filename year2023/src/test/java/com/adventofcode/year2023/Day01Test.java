package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 1)
class Day01Test {

    @Test
    void examplePartOne() {
        var input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        var scanner = new Scanner(input);
        Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(142);
    }

    @Test
    void examplePartTwo() {
        var input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        var scanner = new Scanner(input);
        Assertions.assertThat(Day01.PartTwo.sumCalibrationValues(scanner)).isEqualTo(281);
    }

    @Test
    void partOne(Scanner scanner) {
        Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(55712);
    }

    @Test
    void partTwo(Scanner scanner) {
        Assertions.assertThat(Day01.PartTwo.sumCalibrationValues(scanner)).isEqualTo(55413);
    }
}
