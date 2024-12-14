package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test extends AbstractTest {
    Day08Test() {
        super(2018, 8);
    }

    @Test
    void inputExample() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        assertThat(Day08.readNode(new Scanner(input))).isEqualTo(138);
        assertThat(Day08.rootNode(new Scanner(input))).isEqualTo(66);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day08.readNode(scanner)).isEqualTo(47112);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day08.rootNode(scanner)).isEqualTo(28237);
    }

}
