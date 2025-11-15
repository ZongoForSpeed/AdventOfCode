package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test extends AbstractTest {
    Day01Test() {
        super(2022, 1);
    }

    @Test
    void sampleInput() {
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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day01.PartOne.maxCalorieCounting(scanner)).isEqualTo(69693);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day01.PartTwo.topCalorieCounting(scanner)).isEqualTo(200945);
    }

}
