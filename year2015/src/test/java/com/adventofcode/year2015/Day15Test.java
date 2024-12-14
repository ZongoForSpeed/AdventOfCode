package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test extends AbstractTest {
    Day15Test() {
        super(2015, 15);
    }

    @Test
    void inputExample() {
        String input = """
                Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3""";

        assertThat(Day15.highestCookieScore(new Scanner(input), false)).isEqualTo(62842880);
        assertThat(Day15.highestCookieScore(new Scanner(input), true)).isEqualTo(57600000);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day15.highestCookieScore(scanner, false)).isEqualTo(222870);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day15.highestCookieScore(scanner, true)).isEqualTo(117936);
    }

}
