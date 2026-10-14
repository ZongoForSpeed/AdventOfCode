package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 6)
class Day06Test {
    @Test
    void inputExample() {
        assertThat(Day06.memoryReallocationPartOne("0 2 7 0")).isEqualTo(5);
        assertThat(Day06.memoryReallocationPartTwo("0 2 7 0")).isEqualTo(4);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day06.memoryReallocationPartOne(scanner.nextLine())).isEqualTo(11137);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day06.memoryReallocationPartTwo(scanner.nextLine())).isEqualTo(1037);
    }

}
