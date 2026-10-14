package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;




import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 7)
class Day07Test {
    @Test
    void inputExample() {
        var input = "16,1,2,0,4,2,7,1,2,14";
        assertThat(Day07.fuelCost(input, Day07::fuelPartOne)).isEqualTo(37);
        assertThat(Day07.fuelCost(input, Day07::fuelPartTwo)).isEqualTo(168);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartOne)).isEqualTo(349357);

    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day07.fuelCost(scanner.nextLine(), Day07::fuelPartTwo)).isEqualTo(96708205);

    }
}
