package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 13)
class Day13Test {
    @Test
    void inputExample() {
        var input = """
                0: 3
                1: 2
                4: 4
                6: 4""";

        int tripSeverity = Day13.getTripSeverity(new Scanner(input));
        assertThat(tripSeverity).isEqualTo(24);

        int findBestDelay = Day13.findBestDelay(new Scanner(input));
        assertThat(findBestDelay).isEqualTo(10);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day13.getTripSeverity(scanner)).isEqualTo(2164);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day13.findBestDelay(scanner)).isEqualTo(3861798);
    }

}
