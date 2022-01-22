package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {

    @Test
    void inputExample() {
        assertThat(Day01.countFloor("(())")).isZero();
        assertThat(Day01.countFloor("()()")).isZero();
        assertThat(Day01.countFloor("(((")).isEqualTo(3);
        assertThat(Day01.countFloor("(()(()(")).isEqualTo(3);
        assertThat(Day01.countFloor("))(((((")).isEqualTo(3);
        assertThat(Day01.countFloor("())")).isEqualTo(-1);
        assertThat(Day01.countFloor("))(")).isEqualTo(-1);
        assertThat(Day01.countFloor(")))")).isEqualTo(-3);
        assertThat(Day01.countFloor(")())())")).isEqualTo(-3);

        assertThat(Day01.findBasement(")")).isEqualTo(1);
        assertThat(Day01.findBasement("()()))")).isEqualTo(5);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2015/day/1/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.countFloor(scanner.nextLine())).isEqualTo(232);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2015/day/1/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.findBasement(scanner.nextLine())).isEqualTo(1783);
        }
    }

}
