package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test {

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2021/day/24/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day24.aluPartOne(scanner)).isEqualTo(39924989499969L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2021/day/24/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day24.aluPartTwo(scanner)).isEqualTo(16811412161117L);
        }
    }

}
