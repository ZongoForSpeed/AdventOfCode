package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 18)
class Day18Test {
    @Test
    void inputExample() {
        var input = """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)""";

        {
            var scanner = new Scanner(input);
            long digPlan = Day18.PartOne.readDigPlan(scanner);
            Assertions.assertThat(digPlan).isEqualTo(62);
        }

        {
            var scanner = new Scanner(input);
            long digPlan = Day18.PartTwo.readDigPlan(scanner);
            Assertions.assertThat(digPlan).isEqualTo(952408144115L);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long digPlan = Day18.PartOne.readDigPlan(scanner);
        Assertions.assertThat(digPlan).isEqualTo(31171);
    }

    @Test
    void partTwo(Scanner scanner) {
        long digPlan = Day18.PartTwo.readDigPlan(scanner);
        Assertions.assertThat(digPlan).isEqualTo(131431655002266L);
    }

}
