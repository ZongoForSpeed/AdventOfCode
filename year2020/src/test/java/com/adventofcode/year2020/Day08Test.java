package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AdventOfCode;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 8)
class Day08Test {
    @Test
    void handheldGameConsole() {
        var program = List.of("nop +0",
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
    void partOne(Scanner scanner) {
        var program = FileUtils.readLines(scanner);

        assertThat(Day08.runHandheldGameConsole(program)).isEqualTo(Pair.of(1217L, false));
    }

    @Test
    void partTwo(Scanner scanner) {
        var program = FileUtils.readLines(scanner);

        assertThat(Day08.fixHandheldGameConsole(program)).isEqualTo(501);
    }
}
