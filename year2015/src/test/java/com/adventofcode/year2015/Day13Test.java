package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test {

    @Test
    void inputExample() {
        String input = """
                Alice would gain 54 happiness units by sitting next to Bob.
                Alice would lose 79 happiness units by sitting next to Carol.
                Alice would lose 2 happiness units by sitting next to David.
                Bob would gain 83 happiness units by sitting next to Alice.
                Bob would lose 7 happiness units by sitting next to Carol.
                Bob would lose 63 happiness units by sitting next to David.
                Carol would lose 62 happiness units by sitting next to Alice.
                Carol would gain 60 happiness units by sitting next to Bob.
                Carol would gain 55 happiness units by sitting next to David.
                David would gain 46 happiness units by sitting next to Alice.
                David would lose 7 happiness units by sitting next to Bob.
                David would gain 41 happiness units by sitting next to Carol.""";

        Scanner scanner = new Scanner(input);

        assertThat(Day13.computeMaxHappiness(scanner, false)).isEqualTo(330);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2015/day/13/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.computeMaxHappiness(scanner, false)).isEqualTo(733);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2015/day/13/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.computeMaxHappiness(scanner, true)).isEqualTo(725);
        }
    }
}
