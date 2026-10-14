package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 8)
class Day08Test {
    @Test
    void inputExample() {
        var input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        assertThat(Day08.readNode(new Scanner(input))).isEqualTo(138);
        assertThat(Day08.rootNode(new Scanner(input))).isEqualTo(66);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day08.readNode(scanner)).isEqualTo(47112);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day08.rootNode(scanner)).isEqualTo(28237);
    }

}
