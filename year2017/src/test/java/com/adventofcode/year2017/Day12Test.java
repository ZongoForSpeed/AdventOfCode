package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test extends AbstractTest {
    Day12Test() {
        super(2017, 12);
    }

    @Test
    void inputExample() {
        String input = """
                0 <-> 2
                1 <-> 1
                2 <-> 0, 3, 4
                3 <-> 2, 4
                4 <-> 2, 3, 6
                5 <-> 6
                6 <-> 4, 5""";

        assertThat(Day12.digitalPlumberPartOne(new Scanner(input))).hasSize(6).contains(0, 2, 6, 4, 3, 5);
        assertThat(Day12.digitalPlumberPartTwo(new Scanner(input))).hasSize(2).contains(0, 1);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day12.digitalPlumberPartOne(scanner)).hasSize(130);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day12.digitalPlumberPartTwo(scanner)).hasSize(189);
    }
}
