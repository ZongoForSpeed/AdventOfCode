package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day11Test {

    @Test
    void inputExample() {
        assertThat(Day11.hexGridDistance("ne,ne,ne")).isEqualTo(3);
        assertThat(Day11.hexGridDistance("ne,ne,sw,sw")).isZero();
        assertThat(Day11.hexGridDistance("ne,ne,s,s")).isEqualTo(2);
        assertThat(Day11.hexGridDistance("se,sw,se,sw,sw")).isEqualTo(3);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day11Test.class.getResourceAsStream("/2017/day/11/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day11.hexGridDistance(scanner.nextLine())).isEqualTo(824);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day11Test.class.getResourceAsStream("/2017/day/11/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day11.maxGridDistance(scanner.nextLine())).isEqualTo(1548);
        }
    }
}
