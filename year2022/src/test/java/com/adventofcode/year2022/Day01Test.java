package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void testSampleInput() {
        String input = """
                1000
                2000
                3000
                                
                4000
                                
                5000
                6000
                                
                7000
                8000
                9000
                                
                10000""";

        assertThat(Day01.PartOne.maxCalorieCounting(new Scanner(input))).isEqualTo(24000);

        assertThat(Day01.PartTwo.topCalorieCounting(new Scanner(input))).isEqualTo(45000);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2022/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.PartOne.maxCalorieCounting(scanner)).isEqualTo(69693);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2022/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.PartTwo.topCalorieCounting(scanner)).isEqualTo(200945);
        }
    }

}
