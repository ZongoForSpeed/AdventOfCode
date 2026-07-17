package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day16Test extends AbstractTest {
    Day16Test() {
        super(2023, 16);
    }

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
            var count = Day16.PartOne.countEnergizedTiles(scanner);
            Assertions.assertThat(count).isEqualTo(46);
        }

        {
            var scanner = new Scanner(input);
            var max = Day16.PartTwo.maxEnergizedTiles(scanner);
            Assertions.assertThat(max).isEqualTo(51);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var count = Day16.PartOne.countEnergizedTiles(scanner);
        Assertions.assertThat(count).isEqualTo(6816);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var max = Day16.PartTwo.maxEnergizedTiles(scanner);
        Assertions.assertThat(max).isEqualTo(8163);
    }

}
