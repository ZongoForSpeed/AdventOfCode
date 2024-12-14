package com.adventofcode.year2023;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2023, 21);
    }

    @Test
    void inputExample() {
        String input = """
                ...........
                .....###.#.
                .###.##..#.
                ..#.#...#..
                ....#.#....
                .##..S####.
                .##..#...#.
                .......##..
                .##.#.####.
                .##..##.##.
                ...........""";

        {
            Scanner scanner = new Scanner(input);
            List<Point2D> current = Day21.PartOne.stepCounter(scanner, 6);
            Assertions.assertThat(current).hasSize(16);
        }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 10)).isEqualTo(50);
        // }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 50)).isEqualTo(1594);
        // }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 100)).isEqualTo(6536);
        // }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 500)).isEqualTo(167004);
        // }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 1000)).isEqualTo(668697);
        // }

        // {
        //     Scanner scanner = new Scanner(input);
        //     Assertions.assertThat(PartTwo.infiniteStepCounter(scanner, 5000)).isEqualTo(16733044);
        // }
    }

    @Override
    public void partOne(Scanner scanner) {
        List<Point2D> current = Day21.PartOne.stepCounter(scanner, 64);
        Assertions.assertThat(current).hasSize(3637);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long counter = Day21.PartTwo.infiniteStepCounter(scanner, 26501365);
        Assertions.assertThat(counter).isEqualTo(601113643448699L);
    }

}
