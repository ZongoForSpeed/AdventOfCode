package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2019, 21);
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        String command = """
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

    @Override
    public void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        String command = """
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
