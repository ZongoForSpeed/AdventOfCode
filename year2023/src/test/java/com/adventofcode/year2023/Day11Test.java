package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 11)
class Day11Test {
    @Test
    void inputExample() {
        var input = """
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
            var scanner = new Scanner(input);
            long sumLength = Day11.PartOne.computeGalaxyLength(scanner);

            Assertions.assertThat(sumLength).isEqualTo(374);
        }

        {
            var scanner = new Scanner(input);
            long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 10);

            Assertions.assertThat(sumLength).isEqualTo(1030);
        }

        {
            var scanner = new Scanner(input);
            long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 100);

            Assertions.assertThat(sumLength).isEqualTo(8410);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long sumLength = Day11.PartOne.computeGalaxyLength(scanner);
        Assertions.assertThat(sumLength).isEqualTo(9608724);
    }

    @Test
    void partTwo(Scanner scanner) {
        long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 1_000_000);
        Assertions.assertThat(sumLength).isEqualTo(904633799472L);
    }
}
