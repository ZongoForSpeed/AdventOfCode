package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2015/day/6/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.computeLights(scanner)).isEqualTo(377891);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2015/day/6/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.computeBrightness(scanner)).isEqualTo(14110788);
        }
    }
}
