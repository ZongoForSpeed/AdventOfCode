package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day25Test extends AbstractTest {
    Day25Test() {
        super(2017, 25);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day25.diagnosticChecksum(scanner)).isEqualTo(3099);
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }

}
