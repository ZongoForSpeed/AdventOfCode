package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test extends AbstractTest {
    Day18Test() {
        super(2018, 18);
    }

    @Test
    void example() {
        String input = """
                .#.#...|#.
                .....#|##|
                .|..|...#.
                ..|#.....#
                #.#|||#|#|
                ...#.||...
                .|....|...
                ||...#|.#|
                |.||||..|.
                ...#.|..|.""";

        long value = Day18.treesAndLumberyards(new Scanner(input), 10);
        assertThat(value).isEqualTo(1147);

    }

    @Override
    public void partOne(Scanner scanner) {
        long value = Day18.treesAndLumberyards(scanner, 10);
        assertThat(value).isEqualTo(519552);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long value = Day18.treesAndLumberyards(scanner, 1_000_000_000);
        assertThat(value).isEqualTo(165376);
    }

}
