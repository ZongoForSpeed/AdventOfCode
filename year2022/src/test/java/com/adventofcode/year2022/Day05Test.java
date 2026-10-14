package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2022, day = 5)
class Day05Test {
    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void inputExample() {
        String input = """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3\s
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2""";

        try (Scanner scanner = new Scanner(input)) {
            String topCrate = Day05.PartOne.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("CMZ");
        }

        try (Scanner scanner = new Scanner(input)) {
            String topCrate = Day05.PartTwo.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("MCD");
        }
    }

    @Test
    void partOne(Scanner scanner) {
        String topCrate = Day05.PartOne.supplyStacks(scanner);
        Assertions.assertThat(topCrate).isEqualTo("NTWZZWHFV");
    }


    @Test
    void partTwo(Scanner scanner) {
        String topCrate = Day05.PartTwo.supplyStacks(scanner);
        Assertions.assertThat(topCrate).isEqualTo("BRZGFVBTJ");
    }

}
