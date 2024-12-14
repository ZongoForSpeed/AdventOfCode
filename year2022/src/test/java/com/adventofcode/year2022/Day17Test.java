package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test extends AbstractTest {
    Day17Test() {
        super(2022, 17);
    }

    @Test
    void testExample() {
        String input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

        assertThat(Day17.pyroclasticFlow(input, 10, true)).isEqualTo(17);

        assertThat(Day17.pyroclasticFlow(input, 2022, false)).isEqualTo(3068);
        assertThat(Day17.pyroclasticFlow(input, 1_000_000_000_000L, false)).isEqualTo(1514285714288L);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 2022, false)).isEqualTo(3227);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 1_000_000_000_000L, false)).isEqualTo(1597714285698L);
    }

}
