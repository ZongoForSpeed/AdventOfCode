package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 16)
class Day16Test {
    @Test
    void inputExample() {
        var input = """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....""";

        {
            var scanner = new Scanner(input);
            long count = Day16.PartOne.countEnergizedTiles(scanner);
            Assertions.assertThat(count).isEqualTo(46);
        }

        {
            var scanner = new Scanner(input);
            long max = Day16.PartTwo.maxEnergizedTiles(scanner);
            Assertions.assertThat(max).isEqualTo(51);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long count = Day16.PartOne.countEnergizedTiles(scanner);
        Assertions.assertThat(count).isEqualTo(6816);
    }

    @Test
    void partTwo(Scanner scanner) {
        long max = Day16.PartTwo.maxEnergizedTiles(scanner);
        Assertions.assertThat(max).isEqualTo(8163);
    }

}
