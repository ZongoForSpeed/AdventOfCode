package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day25Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day25Test.class.getResourceAsStream("/2016/day/25/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day25.findClockSignal(scanner)).isEqualTo(189);
        }
    }
}
