package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day07Test extends AbstractTest {

    Day07Test() {
        super(2023, 7);
    }

    @Test
    void inputExample() {
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";
        {
            Scanner scanner = new Scanner(input);

            long winnings = Day07.PartOne.computeWinnings(scanner);

            Assertions.assertThat(winnings).isEqualTo(6440);
        }
        {
            Scanner scanner = new Scanner(input);

            long winnings = Day07.PartTwo.computeWinnings(scanner);

            Assertions.assertThat(winnings).isEqualTo(5905);

        }
    }

    @Override
    public void partOne(Scanner scanner) {
        long winnings = Day07.PartOne.computeWinnings(scanner);
        Assertions.assertThat(winnings).isEqualTo(253866470);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long winnings = Day07.PartTwo.computeWinnings(scanner);
        Assertions.assertThat(winnings).isEqualTo(254494947);
    }

}
