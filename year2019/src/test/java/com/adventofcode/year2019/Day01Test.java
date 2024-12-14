package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test extends AbstractTest {
    Day01Test() {
        super(2019, 1);
    }

    @Test
    void massOf12() {
        assertThat(Day01.fuelRequirements(12)).isEqualTo(2);
    }

    @Test
    void massOf14() {
        assertThat(Day01.fuelRequirements(14)).isEqualTo(2);
        assertThat(Day01.sumFuelRequirements(14)).isEqualTo(2);
    }

    @Test
    void massOf1969() {
        assertThat(Day01.fuelRequirements(1969)).isEqualTo(654);
        assertThat(Day01.sumFuelRequirements(1969)).isEqualTo(966);
    }

    @Test
    void massOf100756() {
        assertThat(Day01.fuelRequirements(100756)).isEqualTo(33583);
        assertThat(Day01.sumFuelRequirements(100756)).isEqualTo(50346);
    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines.stream().mapToLong(Long::valueOf).map(Day01::fuelRequirements).sum()).isEqualTo(3369286);

    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines.stream().mapToLong(Long::valueOf).map(Day01::sumFuelRequirements).sum()).isEqualTo(5051054);
    }
}
