package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 7)
class Day07Test {

    @Test
    void inputExample() {
        var input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";
        {
            var scanner = new Scanner(input);

            long winnings = Day07.PartOne.computeWinnings(scanner);

            Assertions.assertThat(winnings).isEqualTo(6440);
        }
        {
            var scanner = new Scanner(input);

            long winnings = Day07.PartTwo.computeWinnings(scanner);

            Assertions.assertThat(winnings).isEqualTo(5905);

        }
    }

    @Test
    void partOne(Scanner scanner) {
        long winnings = Day07.PartOne.computeWinnings(scanner);
        Assertions.assertThat(winnings).isEqualTo(253866470);
    }

    @Test
    void partTwo(Scanner scanner) {
        long winnings = Day07.PartTwo.computeWinnings(scanner);
        Assertions.assertThat(winnings).isEqualTo(254494947);
    }

}
