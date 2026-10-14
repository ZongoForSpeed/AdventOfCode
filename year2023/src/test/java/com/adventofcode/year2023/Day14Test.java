package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 14)
class Day14Test {
    @Test
    void inputExample() {
        var input = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....""";
        {
            var scanner = new Scanner(input);
            int totalLoad = Day14.PartOne.findTotalLoad(scanner);
            Assertions.assertThat(totalLoad).isEqualTo(136);
        }

        {
            var scanner = new Scanner(input);
            int totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
            Assertions.assertThat(totalLoad).isEqualTo(64);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int totalLoad = Day14.PartOne.findTotalLoad(scanner);
        Assertions.assertThat(totalLoad).isEqualTo(109385);
    }

    @Test
    void partTwo(Scanner scanner) {
        int totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
        Assertions.assertThat(totalLoad).isEqualTo(93102);
    }

}
