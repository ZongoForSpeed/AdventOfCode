package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day17Test {

    @Test
    void inputExample1() {
        String input = """
                2413432311323
                3215453535623
                3255245654254
                3446585845452
                4546657867536
                1438598798454
                4457876987766
                3637877979653
                4654967986887
                4564679986453
                1224686865563
                2546548887735
                4322674655533""";

        {
            Scanner scanner = new Scanner(input);
            int heatLoss = Day17.PartOne.clumsyCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(102);
        }

        {
            Scanner scanner = new Scanner(input);
            int heatLoss = Day17.PartTwo.ultraCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(94);
        }
    }


    @Test
    void inputExample2() {
        String input = """
                111111111111
                999999999991
                999999999991
                999999999991
                999999999991""";

        {
            Scanner scanner = new Scanner(input);
            int heatLoss = Day17.PartTwo.ultraCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(71);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day17Test.class.getResourceAsStream("/2023/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int heatLoss = Day17.PartOne.clumsyCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(936);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day17Test.class.getResourceAsStream("/2023/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int heatLoss = Day17.PartTwo.ultraCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(1157);
        }
    }

}
