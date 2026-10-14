package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 1)
class Day01Test {

    @Test
    void inputExample() {
        assertThat(Day01.computeDistancePartOne("R2, L3")).isEqualTo(5);
        assertThat(Day01.computeDistancePartOne("R2, R2, R2")).isEqualTo(2);
        assertThat(Day01.computeDistancePartOne("R5, L5, R5, R3")).isEqualTo(12);

        assertThat(Day01.computeDistancePartTwo("R8, R4, R4, R8")).isEqualTo(4);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day01.computeDistancePartOne(scanner.nextLine())).isEqualTo(234);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day01.computeDistancePartTwo(scanner.nextLine())).isEqualTo(113);
    }

}
