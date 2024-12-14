package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day03Test extends AbstractTest {
    Day03Test() {
        super(2022, 3);
    }

    @Test
    void inputExample() {
        String input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw""";

        {
            Scanner scanner = new Scanner(input);
            int sum = Day03.PartOne.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(157);
        }

        {
            Scanner scanner = new Scanner(input);
            int sum = Day03.PartTwo.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(70);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        int priority = Day03.PartOne.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(8298);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int priority = Day03.PartTwo.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(2708);
    }

}
