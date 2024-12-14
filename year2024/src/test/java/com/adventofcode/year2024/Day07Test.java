package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test extends AbstractTest {

    protected Day07Test() {
        super(2024, 7);
    }

    @Test
    void inputExample() {
        String input = """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20""";

        try (Scanner scanner = new Scanner(input)) {
            long result = Day07.partOne(scanner);
            assertThat(result).isEqualTo(3749);
        }

        try (Scanner scanner = new Scanner(input)) {
            long result = Day07.partTwo(scanner);
            assertThat(result).isEqualTo(11387L);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day07.partOne(scanner)).isEqualTo(3351424677624L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day07.partTwo(scanner)).isEqualTo(204976636995111L);
    }

}
