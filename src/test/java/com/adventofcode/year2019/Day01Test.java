package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {

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

    @Test
    void massOfInput() throws IOException {
        List<String> lines = FileUtils.readLines("/2019/day/1/input");
        assertThat(lines.stream().mapToLong(Long::valueOf).map(Day01::fuelRequirements).sum()).isEqualTo(3369286);
        assertThat(lines.stream().mapToLong(Long::valueOf).map(Day01::sumFuelRequirements).sum()).isEqualTo(5051054);
    }
}
