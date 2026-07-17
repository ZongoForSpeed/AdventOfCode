package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day17Test extends AbstractTest {

    Day17Test() {
        super(2023, 17);
    }

    @Test
    void inputExample1() {
        var input = """
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
            var scanner = new Scanner(input);
            var heatLoss = Day17.PartOne.clumsyCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(102);
        }

        {
            var scanner = new Scanner(input);
            var heatLoss = Day17.PartTwo.ultraCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(94);
        }
    }


    @Test
    void inputExample2() {
        var input = """
                111111111111
                999999999991
                999999999991
                999999999991
                999999999991""";

        {
            var scanner = new Scanner(input);
            var heatLoss = Day17.PartTwo.ultraCrucible(scanner);
            Assertions.assertThat(heatLoss).isEqualTo(71);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var heatLoss = Day17.PartOne.clumsyCrucible(scanner);
        Assertions.assertThat(heatLoss).isEqualTo(936);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var heatLoss = Day17.PartTwo.ultraCrucible(scanner);
        Assertions.assertThat(heatLoss).isEqualTo(1157);
    }

}
