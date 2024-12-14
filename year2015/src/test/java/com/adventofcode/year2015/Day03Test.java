package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test extends AbstractTest {

    protected Day03Test() {
        super(2015, 3);
    }

    @Test
    void inputExample() {
        assertThat(Day03.countDeliveriesPartOne(">")).isEqualTo(2);
        assertThat(Day03.countDeliveriesPartOne("^>v<")).isEqualTo(4);
        assertThat(Day03.countDeliveriesPartOne("^v^v^v^v^v")).isEqualTo(2);

        assertThat(Day03.countDeliveriesPartTwo(">")).isEqualTo(2);
        assertThat(Day03.countDeliveriesPartTwo("^>v<")).isEqualTo(3);
        assertThat(Day03.countDeliveriesPartTwo("^v^v^v^v^v")).isEqualTo(11);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day03.countDeliveriesPartOne(scanner.nextLine())).isEqualTo(2565);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day03.countDeliveriesPartTwo(scanner.nextLine())).isEqualTo(2639);
    }
}
