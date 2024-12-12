package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {

    @Test
    void inputExample() {
        assertThat(Day06.memoryReallocationPartOne("0 2 7 0")).isEqualTo(5);
        assertThat(Day06.memoryReallocationPartTwo("0 2 7 0")).isEqualTo(4);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2017/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.memoryReallocationPartOne(scanner.nextLine())).isEqualTo(11137);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2017/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.memoryReallocationPartTwo(scanner.nextLine())).isEqualTo(1037);
        }
    }

}
