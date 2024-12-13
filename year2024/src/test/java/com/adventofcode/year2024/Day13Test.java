package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void inputExample() {
        String input = """
                Button A: X+94, Y+34
                Button B: X+22, Y+67
                Prize: X=8400, Y=5400
                
                Button A: X+26, Y+66
                Button B: X+67, Y+21
                Prize: X=12748, Y=12176
                
                Button A: X+17, Y+86
                Button B: X+84, Y+37
                Prize: X=7870, Y=6450
                
                Button A: X+69, Y+23
                Button B: X+27, Y+71
                Prize: X=18641, Y=10279""";

        try (Scanner scanner = new Scanner(input)) {
            long cost = Day13.partOne(scanner);
            assertThat(cost).isEqualTo(480);
        }

        try (Scanner scanner = new Scanner(input)) {
            long cost = Day13.partTwo(scanner);
            assertThat(cost).isEqualTo(875318608908L);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2024/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.partOne(scanner)).isEqualTo(33427L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2024/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.partTwo(scanner)).isEqualTo(91649162972270L);
        }
    }

}
