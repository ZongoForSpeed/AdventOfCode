package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test extends AbstractTest {
    Day02Test() {
        super(2016, 2);
    }

    @Test
    void inputExample() {
        String input = """
                ULL
                RRDDD
                LURDL
                UUUUD""";

        assertThat(Day02.findCodePartOne(new Scanner(input))).isEqualTo("1985");
        assertThat(Day02.findCodePartTwo(new Scanner(input))).isEqualTo("5DB3");
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day02.findCodePartOne(scanner)).isEqualTo("97289");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.findCodePartTwo(scanner)).isEqualTo("9A7DC");
    }

}
