package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day09Test extends AbstractTest {
    Day09Test() {
        super(2017, 9);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day09.score(scanner.nextLine())).isEqualTo(7616);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day09.garbageCount(scanner.nextLine())).isEqualTo(3838);
    }
}
