package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void inputExample() {
        String input = """
                Player 1 starting position: 4
                Player 2 starting position: 8""";

        assertThat(Day21.playPartOne(new Scanner(input))).isEqualTo(739785);
        assertThat(Day21.playPartTwo(new Scanner(input))).isEqualTo(444356092776315L);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2021/day/21/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day21.playPartOne(scanner)).isEqualTo(503478);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2021/day/21/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day21.playPartTwo(scanner)).isEqualTo(716241959649754L);
        }
    }

}
