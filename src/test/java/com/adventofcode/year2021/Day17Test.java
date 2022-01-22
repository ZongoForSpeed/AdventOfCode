package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void inputExample() {
        assertThat(Day17.simulatePartOne("target area: x=20..30, y=-10..-5")).isEqualTo(45);
        assertThat(Day17.simulatePartTwo("target area: x=20..30, y=-10..-5")).isEqualTo(112);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day17Test.class.getResourceAsStream("/2021/day/17/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day17.simulatePartOne(scanner.nextLine())).isEqualTo(3160);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day17Test.class.getResourceAsStream("/2021/day/17/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day17.simulatePartTwo(scanner.nextLine())).isEqualTo(1928);
        }
    }

}
