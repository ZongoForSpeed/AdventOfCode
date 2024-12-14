package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test extends AbstractTest {
    Day01Test() {
        super(2017, 1);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day01.inverseCaptchaPartOne(scanner.nextLine())).isEqualTo(995);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day01.inverseCaptchaPartTwo(scanner.nextLine())).isEqualTo(1130);
    }

}
