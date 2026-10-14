package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2022, day = 3)
class Day03Test {
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
            int sum = Day03.PartOne.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(157);
        }

        {
            var scanner = new Scanner(input);
            int sum = Day03.PartTwo.sumPriorities(scanner);
            Assertions.assertThat(sum).isEqualTo(70);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int priority = Day03.PartOne.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(8298);
    }

    @Test
    void partTwo(Scanner scanner) {
        int priority = Day03.PartTwo.sumPriorities(scanner);
        Assertions.assertThat(priority).isEqualTo(2708);
    }

}
