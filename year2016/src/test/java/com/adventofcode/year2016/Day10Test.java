package com.adventofcode.year2016;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day10Test extends AbstractTest {
    Day10Test() {
        super(2016, 10);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day10.findChips(scanner, IntegerPair.of(17, 61))).isEqualTo(56);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day10.getOutput012(scanner)).isEqualTo(7847);
    }

}
