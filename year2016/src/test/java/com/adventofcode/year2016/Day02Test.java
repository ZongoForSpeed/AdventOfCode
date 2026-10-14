package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 2)
class Day02Test {
    @Test
    void inputExample() {
        var input = """
                ULL
                RRDDD
                LURDL
                UUUUD""";

        assertThat(Day02.findCodePartOne(new Scanner(input))).isEqualTo("1985");
        assertThat(Day02.findCodePartTwo(new Scanner(input))).isEqualTo("5DB3");
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day02.findCodePartOne(scanner)).isEqualTo("97289");
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day02.findCodePartTwo(scanner)).isEqualTo("9A7DC");
    }

}
