package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test extends AbstractTest {
    Day06Test() {
        super(2019, 6);
    }

    @Test
    void examplePartOne() throws Exception {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            Map<String, String> graph = Day06.readGraph(scanner);
            assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "D")).isEqualTo(3);
            assertThat(Day06.internalCountOrbits(graph, new HashMap<>(), "L")).isEqualTo(7);
            assertThat(Day06.countOrbits(graph)).isEqualTo(42);
        }
        try (Scanner scanner = new Scanner(input)) {
            Map<String, String> graph = Day06.readGraph(scanner);
            graph.put("YOU", "K");
            graph.put("SAN", "I");

            assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(4);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        Map<String, String> graph = Day06.readGraph(scanner);
        assertThat(Day06.countOrbits(graph)).isEqualTo(144909);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Map<String, String> graph = Day06.readGraph(scanner);
        assertThat(Day06.countOrbitalTransfers(graph, "YOU", "SAN")).isEqualTo(259);
    }

}
