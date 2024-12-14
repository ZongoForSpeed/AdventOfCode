package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test extends AbstractTest {
    Day14Test() {
        super(2022, 14);
    }

    @Test
    void inputExample() {
        String input = """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day14.partOne(scanner)).isEqualTo(24);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day14.partTwo(scanner)).isEqualTo(93);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day14.partOne(scanner)).isEqualTo(795);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day14.partTwo(scanner)).isEqualTo(30214);
    }
}
