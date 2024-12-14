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
        String input = """
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
            Scanner scanner = new Scanner(input);
            long count = Day16.PartOne.countEnergizedTiles(scanner);
            Assertions.assertThat(count).isEqualTo(46);
        }

        {
            Scanner scanner = new Scanner(input);
            long max = Day16.PartTwo.maxEnergizedTiles(scanner);
            Assertions.assertThat(max).isEqualTo(51);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        long count = Day16.PartOne.countEnergizedTiles(scanner);
        Assertions.assertThat(count).isEqualTo(6816);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long max = Day16.PartTwo.maxEnergizedTiles(scanner);
        Assertions.assertThat(max).isEqualTo(8163);
    }

}
