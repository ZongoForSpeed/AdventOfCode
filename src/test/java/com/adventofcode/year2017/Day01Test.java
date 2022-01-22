package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {

    @Test
    void inputExample() {
        assertThat(Day01.inverseCaptchaPartOne("1122")).isEqualTo(3);
        assertThat(Day01.inverseCaptchaPartOne("1111")).isEqualTo(4);
        assertThat(Day01.inverseCaptchaPartOne("1234")).isZero();
        assertThat(Day01.inverseCaptchaPartOne("91212129")).isEqualTo(9);

        assertThat(Day01.inverseCaptchaPartTwo("1212")).isEqualTo(6);
        assertThat(Day01.inverseCaptchaPartTwo("1221")).isZero();
        assertThat(Day01.inverseCaptchaPartTwo("123425")).isEqualTo(4);
        assertThat(Day01.inverseCaptchaPartTwo("123123")).isEqualTo(12);
        assertThat(Day01.inverseCaptchaPartTwo("12131415")).isEqualTo(4);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2017/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.inverseCaptchaPartOne(scanner.nextLine())).isEqualTo(995);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2017/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.inverseCaptchaPartTwo(scanner.nextLine())).isEqualTo(1130);
        }
    }

}
