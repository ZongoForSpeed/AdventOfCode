package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day08Test extends AbstractTest {
    Day08Test() {
        super(2022, 8);
    }

    @Test
    void inputExample() {
        var input = """
                30373
                25512
                65332
                33549
                35390""";

        {
            var scanner = new Scanner(input);
            var cardinality = Day08.PartOne.countVisibleTrees(scanner);
            Assertions.assertThat(cardinality).isEqualTo(21);
        }

        {
            var scanner = new Scanner(input);
            var maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
            Assertions.assertThat(maxScenicScore).isEqualTo(8);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var cardinality = Day08.PartOne.countVisibleTrees(scanner);
        Assertions.assertThat(cardinality).isEqualTo(1820);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
        Assertions.assertThat(maxScenicScore).isEqualTo(385112);
    }
}
