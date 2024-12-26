package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test extends AbstractTest {

    Day23Test() {
        super(2022, 23);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        int freeSpace = Day23.partOne(scanner);
        assertThat(freeSpace).isEqualTo(4000);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        int rounds = Day23.partTwo(scanner);
        assertThat(rounds).isEqualTo(1040);
    }

    @Test
    void inputExample1() {
        String input = """
                .....
                ..##.
                ..#..
                .....
                ..##.
                .....""";

        try (Scanner scanner = new Scanner(input)) {
            Day23.partOne(scanner);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                ..............
                ..............
                .......#......
                .....###.#....
                ...#...#.#....
                ....#...##....
                ...#.###......
                ...##.#.##....
                ....#..#......
                ..............
                ..............
                ..............""";

        try (Scanner scanner = new Scanner(input)) {
            int freeSpace = Day23.partOne(scanner);
            assertThat(freeSpace).isEqualTo(110);
        }

        try (Scanner scanner = new Scanner(input)) {
            int rounds = Day23.partTwo(scanner);
            assertThat(rounds).isEqualTo(20);
        }
    }

}
