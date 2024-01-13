package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test {

    @Test
    void inputExample() {
        assertThat(Day02.wrappingPaper("2x3x4")).isEqualTo(58);
        assertThat(Day02.wrappingPaper("1x1x10")).isEqualTo(43);
        assertThat(Day02.wrappingBow("2x3x4")).isEqualTo(34);
        assertThat(Day02.wrappingBow("1x1x10")).isEqualTo(14);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2015/day/2/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day02.wrappingPaper(scanner)).isEqualTo(1588178);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2015/day/2/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day02.wrappingBow(scanner)).isEqualTo(3783758);
        }
    }
}
