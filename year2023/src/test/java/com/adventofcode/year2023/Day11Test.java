package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day11Test extends AbstractTest {
    Day11Test() {
        super(2023, 11);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        long sumLength = Day11.PartOne.computeGalaxyLength(scanner);
        Assertions.assertThat(sumLength).isEqualTo(9608724);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long sumLength = Day11.PartTwo.computeGalaxyLength(scanner, 1_000_000);
        Assertions.assertThat(sumLength).isEqualTo(904633799472L);
    }
}
