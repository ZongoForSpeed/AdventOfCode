package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test extends AbstractTest {
    Day07Test() {
        super(2021, 7);
    }

    @Test
    void inputExample() {
        String input = "16,1,2,0,4,2,7,1,2,14";
        assertThat(Day07.fuelCost(input, Day07::fuelPartOne)).isEqualTo(37);
        assertThat(Day07.fuelCost(input, Day07::fuelPartTwo)).isEqualTo(168);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartOne)).isEqualTo(349357);

    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartTwo)).isEqualTo(96708205);

    }
}
