package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2022, day = 2)
class Day02Test {
    @Test
    void inputExample() {
        var input = """
                A Y
                B X
                C Z""";

        {
            var scanner = new Scanner(input);
            int score = Day02.PartOne.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(15);
        }

        {
            var scanner = new Scanner(input);
            int score = Day02.PartTwo.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(12);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int score = Day02.PartOne.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(15691);
    }

    @Test
    void partTwo(Scanner scanner) {
        int score = Day02.PartTwo.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(12989);
    }

}
