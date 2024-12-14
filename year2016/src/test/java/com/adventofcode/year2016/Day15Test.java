package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test extends AbstractTest {
    Day15Test() {
        super(2016, 15);
    }

    @Test
    void inputExample() {
        String input = """
                Disc #1 has 5 positions; at time=0, it is at position 4.
                Disc #2 has 2 positions; at time=0, it is at position 1.""";

        Scanner scanner = new Scanner(input);
        assertThat(Day15.findTimePartOne(scanner)).isEqualTo(5);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day15.findTimePartOne(scanner)).isEqualTo(122318);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day15.findTimePartTwo(scanner)).isEqualTo(3208583);
    }

}
