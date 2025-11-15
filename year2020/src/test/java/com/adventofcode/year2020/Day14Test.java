package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test extends AbstractTest {
    Day14Test() {
        super(2020, 14);
    }

    @Test
    void dockingData1() {
        List<String> programs = List.of("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
                "mem[8] = 11",
                "mem[7] = 101",
                "mem[8] = 0");

        assertThat(Day14.runDockingDataProgram1(programs)).isEqualTo(165);
    }

    @Test
    void dockingData2() {
        List<String> programs = List.of("mask = 000000000000000000000000000000X1001X",
                "mem[42] = 100",
                "mask = 00000000000000000000000000000000X0XX",
                "mem[26] = 1");

        assertThat(Day14.runDockingDataProgram2(programs)).isEqualTo(208);
    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> programs = FileUtils.readLines(scanner);

        assertThat(Day14.runDockingDataProgram1(programs)).isEqualTo(15403588588538L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> programs = FileUtils.readLines(scanner);

        assertThat(Day14.runDockingDataProgram2(programs)).isEqualTo(3260587250457L);
    }
}
