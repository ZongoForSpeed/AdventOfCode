package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test {

    @Test
    void inputExample() {
        String input = """
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
    void inputPartOne() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2017/day/13/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.getTripSeverity(scanner)).isEqualTo(2164);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2017/day/13/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day13.findBestDelay(scanner)).isEqualTo(3861798);
        }
    }

}
