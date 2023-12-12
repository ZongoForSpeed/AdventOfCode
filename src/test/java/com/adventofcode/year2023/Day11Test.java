package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day11Test {

    @Test
    void inputExample() {
        String input = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....""";

        {
            Scanner scanner = new Scanner(input);
            long sumLength = Day11.PartOne.computeGalaxyLength(scanner);

            Assertions.assertThat(sumLength).isEqualTo(374);
        }

        {
            Scanner scanner = new Scanner(input);
            long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 10);

            Assertions.assertThat(sumLength).isEqualTo(1030);
        }

        {
            Scanner scanner = new Scanner(input);
            long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 100);

            Assertions.assertThat(sumLength).isEqualTo(8410);
        }
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day11Test.class.getResourceAsStream("/2023/day/11/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long sumLength = Day11.PartOne.computeGalaxyLength(scanner);
            Assertions.assertThat(sumLength).isEqualTo(9608724);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day11Test.class.getResourceAsStream("/2023/day/11/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 1_000_000);

            Assertions.assertThat(sumLength).isEqualTo(904633799472L);

        }
    }
}
