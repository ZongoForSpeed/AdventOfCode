package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test {

    @Test
    void inputExample() {
        String input = """
                #1 @ 1,3: 4x4
                #2 @ 3,1: 4x4
                #3 @ 5,5: 2x2""";

        assertThat(Day03.overlapArea(new Scanner(input))).isEqualTo(4);
        assertThat(Day03.findClaimId(new Scanner(input))).isEqualTo(3);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2018/day/3/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day03.overlapArea(scanner)).isEqualTo(110383);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2018/day/3/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day03.findClaimId(scanner)).isEqualTo(129);
        }
    }

}
