package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test extends AbstractTest {
    Day05Test() {
        super(2021, 5);
    }

    @Test
    void inputExample() {
        String input = """
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2""";


        assertThat(Day05.countOverlapsPartOne(new Scanner(input))).isEqualTo(5);
        assertThat(Day05.countOverlapsPartTwo(new Scanner(input))).isEqualTo(12);

    }

    @Override
    public void partOne(Scanner scanner) {
        int overlaps = Day05.countOverlapsPartOne(scanner);
        assertThat(overlaps).isEqualTo(6572);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int overlaps = Day05.countOverlapsPartTwo(scanner);
        assertThat(overlaps).isEqualTo(21466);
    }
}
