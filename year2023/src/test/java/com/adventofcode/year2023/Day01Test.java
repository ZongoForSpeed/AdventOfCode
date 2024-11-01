package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day01Test {

    @Test
    void testExamplePartOne() {
        String input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        Scanner scanner = new Scanner(input);
        Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(142);
    }

    @Test
    void testExamplePartTwo() {
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


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day01Test.class.getResourceAsStream("/2023/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            Assertions.assertThat(Day01.PartOne.sumCalibrationValues(scanner)).isEqualTo(55712);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day01Test.class.getResourceAsStream("/2023/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            Assertions.assertThat(Day01.PartTwo.sumCalibrationValues(scanner)).isEqualTo(55413);
        }
    }
}
