package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test extends AbstractTest {
    Day01Test() {
        super(2016, 1);
    }


    @Test
    void inputExample() {
        assertThat(Day01.computeDistancePartOne("R2, L3")).isEqualTo(5);
        assertThat(Day01.computeDistancePartOne("R2, R2, R2")).isEqualTo(2);
        assertThat(Day01.computeDistancePartOne("R5, L5, R5, R3")).isEqualTo(12);

        assertThat(Day01.computeDistancePartTwo("R8, R4, R4, R8")).isEqualTo(4);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day01.computeDistancePartOne(scanner.nextLine())).isEqualTo(234);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day01.computeDistancePartTwo(scanner.nextLine())).isEqualTo(113);
    }

}
