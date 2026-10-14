package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 20)
class Day20Test {
    @Test
    void inputExample() {
        var input = """
                5-8
                0-2
                4-7""";

        assertThat(Day20.findFirstIP(new Scanner(input), 9)).isEqualTo(3);
        assertThat(Day20.countIPs(new Scanner(input), 9)).isEqualTo(2);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day20.findFirstIP(scanner, 4294967295L)).isEqualTo(4793564);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day20.countIPs(scanner, 4294967295L)).isEqualTo(146);
    }

}
