package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2019, day = 6)
class Day06Test {
    @Test
    void examplePartOne() throws Exception {
        var input = """
                COM)B
                B)C
                C)D
                D)E
                E)F
                B)G
                G)H
                D)I
                E)J
                J)K
                K)L""";

        try (var scanner = new Scanner(input)) {
            var graph = Day06.readGraph(scanner);
            assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "D")).isEqualTo(3);
            assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "L")).isEqualTo(7);
            assertThat(Day06.countOrbits(graph)).isEqualTo(42);
        }
        try (var scanner = new Scanner(input)) {
            var graph = Day06.readGraph(scanner);
            graph.put("YOU", "K");
            graph.put("SAN", "I");

            assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(4);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        var graph = Day06.readGraph(scanner);
        assertThat(Day06.countOrbits(graph)).isEqualTo(144909);
    }

    @Test
    void partTwo(Scanner scanner) {
        var graph = Day06.readGraph(scanner);
        assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(259);
    }

}
