package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void testPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/21/input");
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

    @Test
    void testPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/21/input");
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
