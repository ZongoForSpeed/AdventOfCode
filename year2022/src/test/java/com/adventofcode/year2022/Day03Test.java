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
        var input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw""";

        {
            var scanner = new Scanner(input);
            var sum = Day03.PartOne.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(157);
        }

        {
            var scanner = new Scanner(input);
            var sum = Day03.PartTwo.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(70);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var priority = Day03.PartOne.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(8298);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var priority = Day03.PartTwo.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(2708);
    }

}
