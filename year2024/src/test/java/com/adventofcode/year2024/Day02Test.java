package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test extends AbstractTest {

    protected Day02Test() {
        super(2024, 2);
    }

    @Test
    void inputExample() {
        var input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9""";

        try (var scanner = new Scanner(input)) {
            var count = Day02.partOne(scanner);

            assertThat(count).isEqualTo(2);
        }

        try (var scanner = new Scanner(input)) {
            var count = Day02.partTwo(scanner);

            assertThat(count).isEqualTo(4);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day02.partOne(scanner)).isEqualTo(359);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.partTwo(scanner)).isEqualTo(418);
    }

}
