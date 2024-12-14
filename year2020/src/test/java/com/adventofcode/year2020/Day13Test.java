package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.LongPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test extends AbstractTest {
    Day13Test() {
        super(2020, 13);
    }

    @Test
    void testShuttleSearch() {
        List<String> notes = List.of(
                "939",
                "7,13,x,x,59,x,31,19"
        );

        long timestamp = Long.parseLong(notes.getFirst());

        long[] buses = Arrays.stream(notes.get(1).split(","))
                .filter(t -> !"x".equals(t))
                .mapToLong(Long::parseLong)
                .toArray();

        LongPair bus = Day13.findBus(timestamp, buses);
        assertThat(bus.left()).isEqualTo(59);
        assertThat(bus.right()).isEqualTo(5);

        assertThat(Day13.solveShuttleSearch(notes.get(1))).isEqualTo(1068781);
    }

    @Test
    void testSolveShuttleSearch() {
        assertThat(Day13.solveShuttleSearch("17,x,13,19")).isEqualTo(3417);
        assertThat(Day13.solveShuttleSearch("67,7,59,61")).isEqualTo(754018);
        assertThat(Day13.solveShuttleSearch("67,x,7,59,61")).isEqualTo(779210);
        assertThat(Day13.solveShuttleSearch("67,7,x,59,61")).isEqualTo(1261476);
        assertThat(Day13.solveShuttleSearch("1789,37,47,1889")).isEqualTo(1202161486);


    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> notes = FileUtils.readLines(scanner);

        long timestamp = Long.parseLong(notes.getFirst());

        long[] buses = Arrays.stream(notes.get(1).split(","))
                .filter(t -> !"x".equals(t))
                .mapToLong(Long::parseLong)
                .toArray();

        LongPair bus = Day13.findBus(timestamp, buses);
        assertThat(bus.left()).isEqualTo(29);
        assertThat(bus.right()).isEqualTo(6);

    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> notes = FileUtils.readLines(scanner);

        assertThat(Day13.solveShuttleSearch(notes.get(1))).isEqualTo(780601154795940L);
    }
}
