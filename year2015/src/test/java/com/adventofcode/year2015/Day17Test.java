package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day17Test extends AbstractTest {
    Day17Test() {
        super(2015, 17);
    }

    @Test
    void inputExample() {
        String input = """
                20
                15
                10
                5
                5""";

        assertThat(Day17.findCombinationsPartOne(new Scanner(input), 25)).isEqualTo(4);
        assertThat(Day17.findCombinationsPartTwo(new Scanner(input), 25)).isEqualTo(3);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day17.findCombinationsPartOne(scanner, 150)).isEqualTo(4372);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day17.findCombinationsPartTwo(scanner, 150)).isEqualTo(4);
    }
}
