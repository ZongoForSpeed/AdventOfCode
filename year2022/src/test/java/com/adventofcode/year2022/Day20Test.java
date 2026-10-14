package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 20)
class Day20Test {
    @Test
    void inputExample() {
        var input = """
                1
                2
                -3
                3
                -2
                0
                4""";

        {
            var scanner = new Scanner(input);
            long decrypt = Day20.decryptPartOne(scanner);
            assertThat(decrypt).isEqualTo(3);
        }

        {
            var scanner = new Scanner(input);
            long decrypt = Day20.decryptPartTwo(scanner);
            assertThat(decrypt).isEqualTo(1623178306L);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day20.decryptPartOne(scanner)).isEqualTo(23321);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day20.decryptPartTwo(scanner)).isEqualTo(1428396909280L);
    }

}
