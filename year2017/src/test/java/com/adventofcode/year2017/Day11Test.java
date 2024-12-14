package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day11Test extends AbstractTest {
    Day11Test() {
        super(2017, 11);
    }

    @Test
    void inputExample() {
        assertThat(Day11.hexGridDistance("ne,ne,ne")).isEqualTo(3);
        assertThat(Day11.hexGridDistance("ne,ne,sw,sw")).isZero();
        assertThat(Day11.hexGridDistance("ne,ne,s,s")).isEqualTo(2);
        assertThat(Day11.hexGridDistance("se,sw,se,sw,sw")).isEqualTo(3);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day11.hexGridDistance(scanner.nextLine())).isEqualTo(824);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day11.maxGridDistance(scanner.nextLine())).isEqualTo(1548);
    }
}
