package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2018, day = 19)
class Day19Test {
    @Test
    void example() {
        var input = """
                #ip 0
                seti 5 0 1
                seti 6 0 2
                addi 0 1 0
                addr 1 2 3
                setr 1 0 0
                seti 8 0 4
                seti 9 0 5""";
        var scanner = new Scanner(input);

        IntList register = Day19.executePartOne(scanner);
        assertThat(register.toIntArray()).containsExactly(6, 5, 6, 0, 0, 9);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day19.executePartOne(scanner).toIntArray()).startsWith(878);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day19.executePartTwo(scanner)).isEqualTo(11510496);
    }

}
