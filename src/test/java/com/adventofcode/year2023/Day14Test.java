package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class Day14Test {

    @Test
    void inputExample() {
        String input = """
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
            Scanner scanner = new Scanner(input);
            int totalLoad = Day14.PartOne.findTotalLoad(scanner);
            Assertions.assertThat(totalLoad).isEqualTo(136);
        }

        {
            Scanner scanner = new Scanner(input);
            int totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
            Assertions.assertThat(totalLoad).isEqualTo(64);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2023/day/14/input"); Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int totalLoad = Day14.PartOne.findTotalLoad(scanner);
            Assertions.assertThat(totalLoad).isEqualTo(109385);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2023/day/14/input"); Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int totalLoad = Day14.PartTwo.findTotalLoad(scanner, 1_000_000_000);
            Assertions.assertThat(totalLoad).isEqualTo(93102);
        }
    }


}
