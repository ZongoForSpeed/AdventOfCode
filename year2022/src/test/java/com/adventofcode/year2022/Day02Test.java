package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day02Test extends AbstractTest {
    Day02Test() {
        super(2022, 2);
    }

    @Test
    void inputExample() {
        String input = """
                A Y
                B X
                C Z""";

        {
            Scanner scanner = new Scanner(input);
            int score = Day02.PartOne.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(15);
        }

        {
            Scanner scanner = new Scanner(input);
            int score = Day02.PartTwo.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(12);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        int score = Day02.PartOne.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(15691);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int score = Day02.PartTwo.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(12989);
    }

}
