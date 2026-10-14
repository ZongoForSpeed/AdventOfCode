package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2019, day = 15)
class Day15Test {

    @Test
    void partOne(Scanner scanner) {
        Integer distanceToOxygen = Day15.partOne(scanner);
        assertThat(distanceToOxygen).isEqualTo(240);
    }

    @Test
    void partTwo(Scanner scanner) {
        int duration = Day15.partTwo(scanner);
        assertThat(duration).isEqualTo(322);
    }

}
