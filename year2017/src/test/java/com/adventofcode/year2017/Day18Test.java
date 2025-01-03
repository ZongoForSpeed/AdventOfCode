package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day18Test extends AbstractTest {

    Day18Test() {
        super(2017, 18);
    }

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

        assertThat(Day18.partOne(new Scanner(input))).isEqualTo(4);
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

        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.partTwo(scanner)).isEqualTo(3);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day18.partOne(scanner)).isEqualTo(7071);
    }

    @Override
    public void partTwo(Scanner scanner) throws InterruptedException {
        assertThat(Day18.partTwo(scanner)).isEqualTo(8001);
    }

}
