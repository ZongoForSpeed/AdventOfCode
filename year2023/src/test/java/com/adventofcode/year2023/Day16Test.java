package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day16Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day16Test.class.getResourceAsStream("/2023/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long count = Day16.PartOne.countEnergizedTiles(scanner);
            Assertions.assertThat(count).isEqualTo(6816);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day16Test.class.getResourceAsStream("/2023/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long max = Day16.PartTwo.maxEnergizedTiles(scanner);
            Assertions.assertThat(max).isEqualTo(8163);
        }
    }

}
