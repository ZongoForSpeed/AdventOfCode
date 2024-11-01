package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    @Test
    void inputExample() {
        String input = """
                Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                Valve BB has flow rate=13; tunnels lead to valves CC, AA
                Valve CC has flow rate=2; tunnels lead to valves DD, BB
                Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
                Valve EE has flow rate=3; tunnels lead to valves FF, DD
                Valve FF has flow rate=0; tunnels lead to valves EE, GG
                Valve GG has flow rate=0; tunnels lead to valves FF, HH
                Valve HH has flow rate=22; tunnel leads to valve GG
                Valve II has flow rate=0; tunnels lead to valves AA, JJ
                Valve JJ has flow rate=21; tunnel leads to valve II""";

        {
            Scanner scanner = new Scanner(input);
            int solution = Day16.solvePartOne(scanner);
            assertThat(solution).isEqualTo(1651);
        }
        {
            Scanner scanner = new Scanner(input);
            int solution = Day16.solvePartTwo(scanner);
            assertThat(solution).isEqualTo(1707);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day16.solvePartOne(scanner)).isEqualTo(2183);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day16.solvePartTwo(scanner)).isEqualTo(2911);
        }
    }

}
