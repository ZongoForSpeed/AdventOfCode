package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {


    @Test
    void inputExample() {
        assertThat(Day01.computeDistancePartOne("R2, L3")).isEqualTo(5);
        assertThat(Day01.computeDistancePartOne("R2, R2, R2")).isEqualTo(2);
        assertThat(Day01.computeDistancePartOne("R5, L5, R5, R3")).isEqualTo(12);

        assertThat(Day01.computeDistancePartTwo("R8, R4, R4, R8")).isEqualTo(4);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2016/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.computeDistancePartOne(scanner.nextLine())).isEqualTo(234);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2016/day/1/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day01.computeDistancePartTwo(scanner.nextLine())).isEqualTo(113);
        }
    }

}
