package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 3)
class Day03Test {

    @Test
    void inputExample() {
        assertThat(Day03.countDeliveriesPartOne(">")).isEqualTo(2);
        assertThat(Day03.countDeliveriesPartOne("^>v<")).isEqualTo(4);
        assertThat(Day03.countDeliveriesPartOne("^v^v^v^v^v")).isEqualTo(2);

        assertThat(Day03.countDeliveriesPartTwo(">")).isEqualTo(2);
        assertThat(Day03.countDeliveriesPartTwo("^>v<")).isEqualTo(3);
        assertThat(Day03.countDeliveriesPartTwo("^v^v^v^v^v")).isEqualTo(11);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day03.countDeliveriesPartOne(scanner.nextLine())).isEqualTo(2565);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day03.countDeliveriesPartTwo(scanner.nextLine())).isEqualTo(2639);
    }
}
