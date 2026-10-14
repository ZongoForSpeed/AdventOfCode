package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 15)
class Day15Test {
    @Test
    void inputExample() {
        var input = """
                Disc #1 has 5 positions; at time=0, it is at position 4.
                Disc #2 has 2 positions; at time=0, it is at position 1.""";

        var scanner = new Scanner(input);
        assertThat(Day15.findTimePartOne(scanner)).isEqualTo(5);
    }


    @Test
    void partOne(Scanner scanner) {
        assertThat(Day15.findTimePartOne(scanner)).isEqualTo(122318);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day15.findTimePartTwo(scanner)).isEqualTo(3208583);
    }

}
