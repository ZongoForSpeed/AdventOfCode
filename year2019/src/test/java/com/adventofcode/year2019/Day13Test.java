package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2019, day = 13)
class Day13Test {
    @Test
    void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        long count = Day13.arkanoidBlockTiles(line);

        assertThat(count).isEqualTo(348);
    }

    @Test
    void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        Day13.Arkanoid game = Day13.gamePartTwo(line);

        assertThat(game.getScore()).isEqualTo(16999);

    }
}
