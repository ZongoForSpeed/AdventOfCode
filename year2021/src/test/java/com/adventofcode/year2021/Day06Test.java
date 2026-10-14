package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 6)
class Day06Test {
    @Test
    void inputExample() {
        var input = "3,4,3,1,2";

        assertThat(Day06.nextDays(input, 18)).isEqualTo(26);
        assertThat(Day06.nextDays(input, 80)).isEqualTo(5934);
        assertThat(Day06.nextDays(input, 256)).isEqualTo(26984457539L);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day06.nextDays(scanner.nextLine(), 80)).isEqualTo(386640);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day06.nextDays(scanner.nextLine(), 256)).isEqualTo(1733403626279L);
    }
}
