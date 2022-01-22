package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {

    @Test
    void inputExample() {
        String input = "16,1,2,0,4,2,7,1,2,14";
        assertThat(Day07.fuelCost(input, Day07::fuelPartOne)).isEqualTo(37);
        assertThat(Day07.fuelCost(input, Day07::fuelPartTwo)).isEqualTo(168);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2021/day/7/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartOne)).isEqualTo(349357);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2021/day/7/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartTwo)).isEqualTo(96708205);
        }
    }
}
