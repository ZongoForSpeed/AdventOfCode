package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2019, day = 21)
class Day21Test {
    @Test
    void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        var command = """
                NOT A T
                NOT B J
                OR T J
                NOT C T
                OR T J
                AND D J
                WALK
                """;
        assertThat(Day21.runSpringscript(line, command)).isEqualTo(19361023);
    }

    @Test
    void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        var command = """
                NOT F J
                OR E J
                OR H J
                AND D J
                NOT C T
                AND T J
                NOT D T
                OR B T
                OR E T
                NOT T T
                OR T J
                NOT A T
                OR T J
                RUN
                """;
        assertThat(Day21.runSpringscript(line, command)).isEqualTo(1141457530);
    }
}
