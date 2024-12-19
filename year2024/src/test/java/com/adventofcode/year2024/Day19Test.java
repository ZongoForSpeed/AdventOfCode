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
        String input = """
                r, wr, b, g, bwu, rb, gb, br
                
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb""";

        try (Scanner scanner = new Scanner(input)) {
            int count = Day19.partOne(scanner);
            assertThat(count).isEqualTo(6);
        }
        try (Scanner scanner = new Scanner(input)) {
            long count = Day19.partTwo(scanner);
            assertThat(count).isEqualTo(16);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        int count = Day19.partOne(scanner);
        assertThat(count).isEqualTo(258);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        long count = Day19.partTwo(scanner);
        assertThat(count).isEqualTo(632423618484345L);
    }

}
