package com.adventofcode.year2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {

    @Test
    void testExamplePartOne() throws IOException {
        Map<String, String> graph = Day06.readGraph("/2019/day/6/example");
        assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "D")).isEqualTo(3);
        assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "L")).isEqualTo(7);
        assertThat(Day06.countOrbits(graph)).isEqualTo(42);
    }

    @Test
    void testInputPartOne() throws IOException {
        Map<String, String> graph = Day06.readGraph("/2019/day/6/input");
        assertThat(Day06.countOrbits(graph)).isEqualTo(144909);
    }

    @Test
    void testExamplePartTwo() throws IOException {
        Map<String, String> graph = Day06.readGraph("/2019/day/6/example");
        graph.put("YOU", "K");
        graph.put("SAN", "I");

        assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(4);
    }

    @Test
    void testInputPartTwo() throws IOException {
        Map<String, String> graph = Day06.readGraph("/2019/day/6/input");
        assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(259);
    }
}
