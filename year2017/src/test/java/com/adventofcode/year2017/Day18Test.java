package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 18)
class Day18Test {

    @Test
    void inputExample() {
        var input = """
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

        assertThat(Day18.partOne(new Scanner(input))).isEqualTo(4);
    }

    @Test
    void inputDuetExample() throws InterruptedException {
        var input = """
                snd 1
                snd 2
                snd p
                rcv a
                rcv b
                rcv c
                rcv d""";

        try (var scanner = new Scanner(input)) {
            assertThat(Day18.partTwo(scanner)).isEqualTo(3);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day18.partOne(scanner)).isEqualTo(7071);
    }

    @Test
    void partTwo(Scanner scanner) throws InterruptedException {
        assertThat(Day18.partTwo(scanner)).isEqualTo(8001);
    }

}
