package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day24Test extends AbstractTest {

    Day24Test() {
        super(2015, 24);
    }

    @Test
    void inputExample() {
        String input = """
                1
                2
                3
                4
                5
                7
                8
                9
                10
                11""";

        assertThat(Day24.quantumEntanglementPartOne(new Scanner(input))).isEqualTo(99);
        assertThat(Day24.quantumEntanglementPartTwo(new Scanner(input))).isEqualTo(44);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day24.quantumEntanglementPartOne(scanner)).isEqualTo(11846773891L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day24.quantumEntanglementPartTwo(scanner)).isEqualTo(80393059);
    }
}
