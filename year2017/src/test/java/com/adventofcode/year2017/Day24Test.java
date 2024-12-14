package com.adventofcode.year2017;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day24Test extends AbstractTest {
    Day24Test() {
        super(2017, 24);
    }

    @Test
    void inputExample() {
        String input = """
                0/2
                2/2
                2/3
                3/4
                3/5
                0/1
                10/1
                9/10""";

        List<IntegerPair> strongestBridge = Day24.findStrongestBridgePartOne(new Scanner(input));
        assertThat(strongestBridge)
                .hasSize(3)
                .containsExactly(IntegerPair.of(0, 1), IntegerPair.of(1, 10), IntegerPair.of(9, 10));

        assertThat(Day24.strength(strongestBridge)).isEqualTo(31);

        strongestBridge = Day24.findStrongestBridgePartTwo(new Scanner(input));
        assertThat(strongestBridge)
                .hasSize(4)
                .containsExactly(IntegerPair.of(0, 2), IntegerPair.of(2, 2), IntegerPair.of(2, 3), IntegerPair.of(3, 5));

        assertThat(Day24.strength(strongestBridge)).isEqualTo(19);
    }

    @Override
    public void partOne(Scanner scanner) {
        List<IntegerPair> strongestBridge = Day24.findStrongestBridgePartOne(scanner);

        assertThat(Day24.strength(strongestBridge)).isEqualTo(2006);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<IntegerPair> strongestBridge = Day24.findStrongestBridgePartTwo(scanner);

        assertThat(Day24.strength(strongestBridge)).isEqualTo(1994);
    }

}
