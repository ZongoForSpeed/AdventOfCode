package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test extends AbstractTest {
    Day01Test() {
        super(2018, 1);
    }

    @Override
    public void partOne(Scanner scanner) {
        long frequency = Day01.addFrequencies(scanner);
        assertThat(frequency).isEqualTo(508);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long frequency = Day01.findDuplicateFrequency(scanner);
        assertThat(frequency).isEqualTo(549);
    }

}
