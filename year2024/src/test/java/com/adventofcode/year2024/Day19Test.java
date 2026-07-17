package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test extends AbstractTest {

    protected Day19Test() {
        super(2024, 19);
    }

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
            var count = Day19.partOne(scanner);
            assertThat(count).isEqualTo(6);
        }
        try (var scanner = new Scanner(input)) {
            var count = Day19.partTwo(scanner);
            assertThat(count).isEqualTo(16);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        var count = Day19.partOne(scanner);
        assertThat(count).isEqualTo(258);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        var count = Day19.partTwo(scanner);
        assertThat(count).isEqualTo(632423618484345L);
    }

}
