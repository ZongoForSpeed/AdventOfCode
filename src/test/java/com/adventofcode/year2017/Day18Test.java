package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day18Test {

    @Test
    void inputExample() {
        String input = """
                set a 1
                add a 2
                mul a a
                mod a 5
                snd a
                set a 0
                rcv a
                jgz a -1
                set a 1
                jgz a -2""";

        assertThat(Day18.playSound(new Scanner(input))).isEqualTo(4);
    }

    @Test
    void inputDuetExample() throws InterruptedException {
        String input = """
                snd 1
                snd 2
                snd p
                rcv a
                rcv b
                rcv c
                rcv d""";

        assertThat(Day18.duet(new Scanner(input))).isEqualTo(3);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day18.playSound(scanner)).isEqualTo(7071);
        }
    }

    @Test
    void inputPartTwo() throws IOException, InterruptedException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day18.duet(scanner)).isEqualTo(8001);
        }
    }

}
