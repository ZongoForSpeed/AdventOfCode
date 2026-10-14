package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 6)
class Day06Test {
    @Test
    void partOne(Scanner scanner) {
        assertThat(Day06.computeLights(scanner)).isEqualTo(377891);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day06.computeBrightness(scanner)).isEqualTo(14110788);
    }
}
