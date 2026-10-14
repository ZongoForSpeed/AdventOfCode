package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 5)
class Day05Test {
    @Test
    void inputExample() {
        String input = "dabAcCaCBAcCcaDA";

        assertThat(Day05.getReductionSize(input, _ -> true)).isEqualTo(10);

        int minSize = Day05.minReductionSize(input);

        assertThat(minSize).isEqualTo(4);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day05.getReductionSize(scanner.nextLine(), _ -> true)).isEqualTo(11310);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day05.minReductionSize(scanner.nextLine())).isEqualTo(6020);
    }

}
