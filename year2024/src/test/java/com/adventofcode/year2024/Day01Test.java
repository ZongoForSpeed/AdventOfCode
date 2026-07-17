package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test extends AbstractTest {

    protected Day01Test() {
        super(2024, 1);
    }

    @Test
    void inputExample() {
        var input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3""";

        try (var scanner = new Scanner(input)) {
            var diff = Day01.partOne(scanner);
            assertThat(diff).isEqualTo(11);
        }

        try (var scanner = new Scanner(input)) {
            var score = Day01.partTwo(scanner);

            assertThat(score).isEqualTo(31);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day01.partOne(scanner)).isEqualTo(1941353);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day01.partTwo(scanner)).isEqualTo(22539317L);
    }
}
