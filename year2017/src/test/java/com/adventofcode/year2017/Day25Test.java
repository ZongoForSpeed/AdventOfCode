package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 25)
class Day25Test {
    @Test
    void inputExample() {
        var input = """
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

        var scanner = new Scanner(input);

        long diagnosticChecksum = Day25.diagnosticChecksum(scanner);
        assertThat(diagnosticChecksum).isEqualTo(3);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day25.diagnosticChecksum(scanner)).isEqualTo(3099);
    }

    @Test
    void partTwo(Scanner scanner) {
        // No-Op
    }

}
