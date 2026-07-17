package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day14Test extends AbstractTest {
    Day14Test() {
        super(2023, 14);
    }

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
            var totalLoad = Day14.PartOne.findTotalLoad(scanner);
            Assertions.assertThat(totalLoad).isEqualTo(136);
        }

        {
            var scanner = new Scanner(input);
            var totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
            Assertions.assertThat(totalLoad).isEqualTo(64);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var totalLoad = Day14.PartOne.findTotalLoad(scanner);
        Assertions.assertThat(totalLoad).isEqualTo(109385);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
        Assertions.assertThat(totalLoad).isEqualTo(93102);
    }

}
