package com.adventofcode.year2018;

import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test {

    @Test
    void example() {
        String input = """
                #ip 0
                seti 5 0 1
                seti 6 0 2
                addi 0 1 0
                addr 1 2 3
                setr 1 0 0
                seti 8 0 4
                seti 9 0 5""";
        Scanner scanner = new Scanner(input);

        IntList register = Day19.executePartOne(scanner);
        assertThat(register.toIntArray()).containsExactly(6, 5, 6, 0, 0, 9);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2018/day/19/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.executePartOne(scanner).toIntArray()).startsWith(878);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2018/day/19/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.executePartTwo(scanner)).isEqualTo(11510496);
        }
    }

}
