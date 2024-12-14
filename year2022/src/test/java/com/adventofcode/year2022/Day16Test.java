package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test extends AbstractTest {
    Day16Test() {
        super(2022, 16);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day16.solvePartOne(scanner)).isEqualTo(2183);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day16.solvePartTwo(scanner)).isEqualTo(2911);
    }

}
