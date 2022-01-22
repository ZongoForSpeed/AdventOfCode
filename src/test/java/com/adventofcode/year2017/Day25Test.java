package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day25Test {

    ;

    @Test
    void inputExample() {
        String input = """
                Begin in state A.
                Perform a diagnostic checksum after 6 steps.
                                
                In state A:
                  If the current value is 0:
                    - Write the value 1.
                    - Move one slot to the right.
                    - Continue with state B.
                  If the current value is 1:
                    - Write the value 0.
                    - Move one slot to the left.
                    - Continue with state B.
                                
                In state B:
                  If the current value is 0:
                    - Write the value 1.
                    - Move one slot to the left.
                    - Continue with state A.
                  If the current value is 1:
                    - Write the value 1.
                    - Move one slot to the right.
                    - Continue with state A.""";

        Scanner scanner = new Scanner(input);

        long diagnosticChecksum = Day25.diagnosticChecksum(scanner);
        assertThat(diagnosticChecksum).isEqualTo(3);
    }

    @Test
    void inputPartTwo() throws IOException, InterruptedException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/25/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day25.diagnosticChecksum(scanner)).isEqualTo(3099);
        }
    }

}
