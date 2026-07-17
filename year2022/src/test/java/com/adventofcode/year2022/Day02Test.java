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
        var input = """
                A Y
                B X
                C Z""";

        {
            var scanner = new Scanner(input);
            var score = Day02.PartOne.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(15);
        }

        {
            var scanner = new Scanner(input);
            var score = Day02.PartTwo.findScore(scanner);
            Assertions.assertThat(score).isEqualTo(12);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var score = Day02.PartOne.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(15691);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var score = Day02.PartTwo.findScore(scanner);
        Assertions.assertThat(score).isEqualTo(12989);
    }

}
