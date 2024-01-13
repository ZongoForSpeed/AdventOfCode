package com.adventofcode.year2016;

import com.adventofcode.utils.IntegerPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day10Test {

    @Test
    void inputExample() {
        String input = """
                value 5 goes to bot 2
                bot 2 gives low to bot 1 and high to bot 0
                value 3 goes to bot 1
                bot 1 gives low to output 1 and high to bot 0
                bot 0 gives low to output 2 and high to output 0
                value 2 goes to bot 2""";

        Scanner scanner = new Scanner(input);

        Day10.BalanceBots balanceBots = new Day10.BalanceBots();
        balanceBots.runBalanceBots(scanner);
        assertThat(balanceBots.getOutputs()).containsAllEntriesOf(Map.of(0, 5, 1, 2, 2, 3));
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2016/day/10/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day10.findChips(scanner, IntegerPair.of(17, 61))).isEqualTo(56);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2016/day/10/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day10.getOutput012(scanner)).isEqualTo(7847);
        }
    }

}
