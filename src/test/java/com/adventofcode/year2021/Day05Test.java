package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

    @Test
    void inputExample() {
        String input = """
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2""";


        assertThat(Day05.countOverlapsPartOne(new Scanner(input))).isEqualTo(5);
        assertThat(Day05.countOverlapsPartTwo(new Scanner(input))).isEqualTo(12);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2021/day/5/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            int overlaps = Day05.countOverlapsPartOne(scanner);
            assertThat(overlaps).isEqualTo(6572);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2021/day/5/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            int overlaps = Day05.countOverlapsPartTwo(scanner);
            assertThat(overlaps).isEqualTo(21466);
        }
    }
}
