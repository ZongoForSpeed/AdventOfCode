package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day07Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2023/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long winnings = Day07.PartOne.computeWinnings(scanner);
            Assertions.assertThat(winnings).isEqualTo(253866470);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2023/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long winnings = Day07.PartTwo.computeWinnings(scanner);
            Assertions.assertThat(winnings).isEqualTo(254494947);
        }
    }


}
