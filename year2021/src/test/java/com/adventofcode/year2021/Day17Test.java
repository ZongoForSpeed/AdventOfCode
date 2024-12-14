package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test extends AbstractTest {
    Day17Test() {
        super(2021, 17);
    }

    @Test
    void inputExample() {
        assertThat(Day17.simulatePartOne("target area: x=20..30, y=-10..-5")).isEqualTo(45);
        assertThat(Day17.simulatePartTwo("target area: x=20..30, y=-10..-5")).isEqualTo(112);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day17.simulatePartOne(scanner.nextLine())).isEqualTo(3160);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day17.simulatePartTwo(scanner.nextLine())).isEqualTo(1928);
    }

}
