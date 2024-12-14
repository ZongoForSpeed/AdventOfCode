package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test extends AbstractTest {
    Day06Test() {
        super(2021, 6);
    }

    @Test
    void inputExample() {
        String input = "3,4,3,1,2";

        assertThat(Day06.nextDays(input, 18)).isEqualTo(26);
        assertThat(Day06.nextDays(input, 80)).isEqualTo(5934);
        assertThat(Day06.nextDays(input, 256)).isEqualTo(26984457539L);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.nextDays(scanner.nextLine(), 80)).isEqualTo(386640);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.nextDays(scanner.nextLine(), 256)).isEqualTo(1733403626279L);
    }
}
