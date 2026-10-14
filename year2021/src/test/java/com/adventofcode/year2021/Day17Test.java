package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 17)
class Day17Test {
    @Test
    void inputExample() {
        assertThat(Day17.simulatePartOne("target area: x=20..30, y=-10..-5")).isEqualTo(45);
        assertThat(Day17.simulatePartTwo("target area: x=20..30, y=-10..-5")).isEqualTo(112);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day17.simulatePartOne(scanner.nextLine())).isEqualTo(3160);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day17.simulatePartTwo(scanner.nextLine())).isEqualTo(1928);
    }

}
