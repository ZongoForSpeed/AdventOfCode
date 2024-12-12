package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test {

    @Test
    void inputExample() {
        String input = "s1,x3/4,pe/b";

        assertThat(Day16.danceMoves(input, 5, 1)).isEqualTo("baedc");
        assertThat(Day16.danceMoves(input, 5, 2)).isEqualTo("ceadb");
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2017/day/16/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1)).isEqualTo("dcmlhejnifpokgba");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2017/day/16/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1_000_000_000)).isEqualTo("ifocbejpdnklamhg");
        }
    }

}
