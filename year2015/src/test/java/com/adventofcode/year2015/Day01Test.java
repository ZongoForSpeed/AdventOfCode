package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 1)
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
    void partOne(Scanner scanner) {
        assertThat(Day01.countFloor(scanner.nextLine())).isEqualTo(232);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day01.findBasement(scanner.nextLine())).isEqualTo(1783);
    }

}
