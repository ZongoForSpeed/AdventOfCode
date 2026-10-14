package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 9)
class Day09Test {
    @Test
    void inputExample() {
        assertThat(Day09.score("{}")).isEqualTo(1);
        assertThat(Day09.score("{{{}}}")).isEqualTo(6);
        assertThat(Day09.score("{{},{}}")).isEqualTo(5);
        assertThat(Day09.score("{{{},{},{{}}}}")).isEqualTo(16);
        assertThat(Day09.score("{<a>,<a>,<a>,<a>}")).isEqualTo(1);
        assertThat(Day09.score("{{<ab>},{<ab>},{<ab>},{<ab>}}")).isEqualTo(9);
        assertThat(Day09.score("{{<!!>},{<!!>},{<!!>},{<!!>}}")).isEqualTo(9);
        assertThat(Day09.score("{{<a!>},{<a!>},{<a!>},{<ab>}}")).isEqualTo(3);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day09.score(scanner.nextLine())).isEqualTo(7616);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day09.garbageCount(scanner.nextLine())).isEqualTo(3838);
    }
}
