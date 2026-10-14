package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2022, day = 8)
class Day08Test {
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
            long cardinality = Day08.PartOne.countVisibleTrees(scanner);
            Assertions.assertThat(cardinality).isEqualTo(21);
        }

        {
            var scanner = new Scanner(input);
            int maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
            Assertions.assertThat(maxScenicScore).isEqualTo(8);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long cardinality = Day08.PartOne.countVisibleTrees(scanner);
        Assertions.assertThat(cardinality).isEqualTo(1820);
    }

    @Test
    void partTwo(Scanner scanner) {
        int maxScenicScore = Day08.PartTwo.maxScenicScore(scanner);
        Assertions.assertThat(maxScenicScore).isEqualTo(385112);
    }
}
