package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test extends AbstractTest {
    Day13Test() {
        super(2017, 13);
    }

    @Test
    void inputExample() {
        String input = """
                0: 3
                1: 2
                4: 4
                6: 4""";

        int tripSeverity = Day13.getTripSeverity(new Scanner(input));
        assertThat(tripSeverity).isEqualTo(24);

        int findBestDelay = Day13.findBestDelay(new Scanner(input));
        assertThat(findBestDelay).isEqualTo(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day13.getTripSeverity(scanner)).isEqualTo(2164);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day13.findBestDelay(scanner)).isEqualTo(3861798);
    }

}
