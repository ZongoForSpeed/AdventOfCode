package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 19)
class Day19Test {

    @Test
    void inputExample() {
        var input = """
                r, wr, b, g, bwu, rb, gb, br
                
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb""";

        try (var scanner = new Scanner(input)) {
            int count = Day19.partOne(scanner);
            assertThat(count).isEqualTo(6);
        }
        try (var scanner = new Scanner(input)) {
            long count = Day19.partTwo(scanner);
            assertThat(count).isEqualTo(16);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int count = Day19.partOne(scanner);
        assertThat(count).isEqualTo(258);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long count = Day19.partTwo(scanner);
        assertThat(count).isEqualTo(632423618484345L);
    }

}
