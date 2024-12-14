package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test extends AbstractTest {

    Day01Test() {
        super(2015, 1);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day01.countFloor(scanner.nextLine())).isEqualTo(232);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day01.findBasement(scanner.nextLine())).isEqualTo(1783);
    }

}
