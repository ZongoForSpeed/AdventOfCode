package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day20Test extends AbstractTest {
    Day20Test() {
        super(2016, 20);
    }

    @Test
    void inputExample() {
        String input = """
                5-8
                0-2
                4-7""";

        assertThat(Day20.findFirstIP(new Scanner(input), 9)).isEqualTo(3);
        assertThat(Day20.countIPs(new Scanner(input), 9)).isEqualTo(2);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day20.findFirstIP(scanner, 4294967295L)).isEqualTo(4793564);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day20.countIPs(scanner, 4294967295L)).isEqualTo(146);
    }

}
