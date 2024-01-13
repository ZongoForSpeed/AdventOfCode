package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


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
    void inputPartOne() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2015/day/3/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day03.countDeliveriesPartOne(scanner.nextLine())).isEqualTo(2565);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2015/day/3/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day03.countDeliveriesPartTwo(scanner.nextLine())).isEqualTo(2639);
        }
    }
}
