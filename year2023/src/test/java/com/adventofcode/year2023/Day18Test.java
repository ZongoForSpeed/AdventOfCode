package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day18Test extends AbstractTest {
    Day18Test() {
        super(2023, 18);
    }

    @Test
    void inputExample() {
        String input = """
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
            Scanner scanner = new Scanner(input);
            long digPlan = Day18.PartOne.readDigPlan(scanner);
            Assertions.assertThat(digPlan).isEqualTo(62);
        }

        {
            Scanner scanner = new Scanner(input);
            long digPlan = Day18.PartTwo.readDigPlan(scanner);
            Assertions.assertThat(digPlan).isEqualTo(952408144115L);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        long digPlan = Day18.PartOne.readDigPlan(scanner);
        Assertions.assertThat(digPlan).isEqualTo(31171);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long digPlan = Day18.PartTwo.readDigPlan(scanner);
        Assertions.assertThat(digPlan).isEqualTo(131431655002266L);
    }

}
