package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import com.adventofcode.utils.LongPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void testShuttleSearch() {
        List<String> notes = List.of(
                "939",
                "7,13,x,x,59,x,31,19"
        );

        long timestamp = Long.parseLong(notes.get(0));

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

    @Test
    void inputShuttleSearch() throws IOException {
        List<String> notes = FileUtils.readLines("/2020/day/13/input");

        long timestamp = Long.parseLong(notes.get(0));

        long[] buses = Arrays.stream(notes.get(1).split(","))
                .filter(t -> !"x".equals(t))
                .mapToLong(Long::parseLong)
                .toArray();

        LongPair bus = Day13.findBus(timestamp, buses);
        assertThat(bus.left()).isEqualTo(29);
        assertThat(bus.right()).isEqualTo(6);

        assertThat(Day13.solveShuttleSearch(notes.get(1))).isEqualTo(780601154795940L);
    }
}
