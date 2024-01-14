package com.adventofcode.year2023;

import com.adventofcode.common.point.Point2D;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Day21Test {

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


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day21Test.class.getResourceAsStream("/2023/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            List<Point2D> current = Day21.PartOne.stepCounter(scanner, 64);
            Assertions.assertThat(current).hasSize(3637);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day21Test.class.getResourceAsStream("/2023/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long counter = Day21.PartTwo.infiniteStepCounter(scanner, 26501365);
            Assertions.assertThat(counter).isEqualTo(601113643448699L);
        }
    }

}
