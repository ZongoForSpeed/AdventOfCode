package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    @Test
    void testDockingData1() {
        List<String> programs = List.of("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
                "mem[8] = 11",
                "mem[7] = 101",
                "mem[8] = 0");

        assertThat(Day14.runDockingDataProgram1(programs)).isEqualTo(165);
    }


    @Test
    void testDockingData2() {
        List<String> programs = List.of("mask = 000000000000000000000000000000X1001X",
                "mem[42] = 100",
                "mask = 00000000000000000000000000000000X0XX",
                "mem[26] = 1");

        assertThat(Day14.runDockingDataProgram2(programs)).isEqualTo(208);
    }

    @Test
    void inputDockingData() throws IOException {
        List<String> programs = FileUtils.readLines("/2020/day/14/input");

        assertThat(Day14.runDockingDataProgram1(programs)).isEqualTo(15403588588538L);

        assertThat(Day14.runDockingDataProgram2(programs)).isEqualTo(3260587250457L);
    }
}
