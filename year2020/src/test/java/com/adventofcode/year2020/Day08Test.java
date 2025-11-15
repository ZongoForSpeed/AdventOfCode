package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test extends AbstractTest {
    Day08Test() {
        super(2020, 8);
    }

    @Test
    void handheldGameConsole() {
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

    @Override
    public void partOne(Scanner scanner) {
        List<String> program = FileUtils.readLines(scanner);

        assertThat(Day08.runHandheldGameConsole(program)).isEqualTo(Pair.of(1217L, false));
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> program = FileUtils.readLines(scanner);

        assertThat(Day08.fixHandheldGameConsole(program)).isEqualTo(501);
    }
}
