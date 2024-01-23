package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day05Test {

    @Test
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

        {
            Scanner scanner = new Scanner(input);
            String topCrate = Day05.PartOne.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("CMZ");
        }

        {
            Scanner scanner = new Scanner(input);
            String topCrate = Day05.PartTwo.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("MCD");
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day05Test.class.getResourceAsStream("/2022/day/05/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            String topCrate = Day05.PartOne.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("NTWZZWHFV");
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day05Test.class.getResourceAsStream("/2022/day/05/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            String topCrate = Day05.PartTwo.supplyStacks(scanner);
            Assertions.assertThat(topCrate).isEqualTo("BRZGFVBTJ");
        }
    }

}
