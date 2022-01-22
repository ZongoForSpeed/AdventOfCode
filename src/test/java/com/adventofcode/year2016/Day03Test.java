package com.adventofcode.year2016;

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
                101 301 501
                102 302 502
                103 303 503
                201 401 601
                202 402 602
                203 403 603""";

        int count = Day03.countTrianglesPartTwo(new Scanner(input));
        assertThat(count).isEqualTo(6);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2016/day/3/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day03.countTrianglesPartOne(scanner)).isEqualTo(1032);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2016/day/3/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day03.countTrianglesPartTwo(scanner)).isEqualTo(1838);
        }
    }

}
