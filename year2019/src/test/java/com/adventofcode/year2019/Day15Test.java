package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test extends AbstractTest {

    Day15Test() {
        super(2019, 15);
    }

    @Override
    public void partOne(Scanner scanner) {
        Integer distanceToOxygen = Day15.partOne(scanner);
        assertThat(distanceToOxygen).isEqualTo(240);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int duration = Day15.partTwo(scanner);
        assertThat(duration).isEqualTo(322);
    }

}
