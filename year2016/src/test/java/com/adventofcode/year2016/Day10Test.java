package com.adventofcode.year2016;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 10)
class Day10Test {
    @Test
    void inputExample() {
        var input = """
                value 5 goes to bot 2
                bot 2 gives low to bot 1 and high to bot 0
                value 3 goes to bot 1
                bot 1 gives low to output 1 and high to bot 0
                bot 0 gives low to output 2 and high to output 0
                value 2 goes to bot 2""";

        var scanner = new Scanner(input);

        var balanceBots = new Day10.BalanceBots();
        balanceBots.runBalanceBots(scanner);
        assertThat(balanceBots.getOutputs()).containsAllEntriesOf(Map.of(0, 5, 1, 2, 2, 3));
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day10.findChips(scanner, IntegerPair.of(17, 61))).isEqualTo(56);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day10.getOutput012(scanner)).isEqualTo(7847);
    }

}
