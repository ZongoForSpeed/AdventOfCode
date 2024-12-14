package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test extends AbstractTest {
    Day05Test() {
        super(2017, 5);
    }

    @Test
    void inputExample() {
        String input = """
                0
                3
                0
                1
                -3""";

        assertThat(Day05.countStepsPartOne(new Scanner(input))).isEqualTo(5);
        assertThat(Day05.countStepsPartTwo(new Scanner(input))).isEqualTo(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day05.countStepsPartOne(scanner)).isEqualTo(378980);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day05.countStepsPartTwo(scanner)).isEqualTo(26889114);
    }

}
