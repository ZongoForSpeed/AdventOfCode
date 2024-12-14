package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test extends AbstractTest {
    Day09Test() {
        super(2021, 9);
    }

    @Test
    void inputExample() {
        String input = """
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678""";

        assertThat(Day09.smokeBasinRisk(new Scanner(input))).isEqualTo(15);
        assertThat(Day09.smokeBasin(new Scanner(input))).isEqualTo(1134);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day09.smokeBasinRisk(scanner)).isEqualTo(524);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day09.smokeBasin(scanner)).isEqualTo(1235430);
    }
}
