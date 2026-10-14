package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 1)
class Day01Test {
    @Test
    void sampleInput() {
        var input = """
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
    void partOne(Scanner scanner) {
        assertThat(Day01.PartOne.maxCalorieCounting(scanner)).isEqualTo(69693);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day01.PartTwo.topCalorieCounting(scanner)).isEqualTo(200945);
    }

}
