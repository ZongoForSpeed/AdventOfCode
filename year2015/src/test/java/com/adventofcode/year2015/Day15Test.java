package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 15)
class Day15Test {
    @Test
    void inputExample() {
        var input = """
                Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3""";

        assertThat(Day15.highestCookieScore(new Scanner(input), false)).isEqualTo(62842880);
        assertThat(Day15.highestCookieScore(new Scanner(input), true)).isEqualTo(57600000);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day15.highestCookieScore(scanner, false)).isEqualTo(222870);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day15.highestCookieScore(scanner, true)).isEqualTo(117936);
    }

}
