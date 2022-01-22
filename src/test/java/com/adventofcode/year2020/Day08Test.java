package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    @Test
    void testHandheldGameConsole() {
        List<String> program = List.of("nop +0",
                "acc +1",
                "jmp +4",
                "acc +3",
                "jmp -3",
                "acc -99",
                "acc +1",
                "jmp -4",
                "acc +6");

        assertThat(Day08.runHandheldGameConsole(program)).isEqualTo(Pair.of(5L, false));

        assertThat(Day08.fixHandheldGameConsole(program)).isEqualTo(8);
    }

    @Test
    void inputHandheldGameConsole() throws IOException {
        List<String> program = FileUtils.readLines("/2020/day/8/input");

        assertThat(Day08.runHandheldGameConsole(program)).isEqualTo(Pair.of(1217L, false));
        assertThat(Day08.fixHandheldGameConsole(program)).isEqualTo(501);
    }
}
