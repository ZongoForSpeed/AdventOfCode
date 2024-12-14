package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test extends AbstractTest {
    Day13Test() {
        super(2019, 13);
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        long count = Day13.arkanoidBlockTiles(line);

        assertThat(count).isEqualTo(348);
    }

    @Override
    public void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        Day13.Arkanoid game = Day13.gamePartTwo(line);

        assertThat(game.getScore()).isEqualTo(16999);

    }
}
