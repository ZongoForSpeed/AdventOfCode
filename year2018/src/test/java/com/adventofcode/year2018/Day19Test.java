package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test extends AbstractTest {
    Day19Test() {
        super(2018, 19);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day19.executePartOne(scanner).toIntArray()).startsWith(878);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day19.executePartTwo(scanner)).isEqualTo(11510496);
    }

}
