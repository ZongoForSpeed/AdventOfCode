package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test extends AbstractTest {
    Day20Test() {
        super(2022, 20);
    }

    @Test
    void inputExample() {
        String input = """
                1
                2
                -3
                3
                -2
                0
                4""";

        {
            Scanner scanner = new Scanner(input);
            long decrypt = Day20.decryptPartOne(scanner);
            assertThat(decrypt).isEqualTo(3);
        }

        {
            Scanner scanner = new Scanner(input);
            long decrypt = Day20.decryptPartTwo(scanner);
            assertThat(decrypt).isEqualTo(1623178306L);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day20.decryptPartOne(scanner)).isEqualTo(23321);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day20.decryptPartTwo(scanner)).isEqualTo(1428396909280L);
    }

}
