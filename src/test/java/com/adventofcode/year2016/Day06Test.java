package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {

    @Test
    void inputExample() {
        String input = """
                eedadn
                drvtee
                eandsr
                raavrd
                atevrs
                tsrnev
                sdttsa
                rasrtv
                nssdts
                ntnada
                svetve
                tesnvt
                vntsnd
                vrdear
                dvrsen
                enarar""";

        assertThat(Day06.decodePasswordMostCommon(new Scanner(input))).isEqualTo("easter");
        assertThat(Day06.decodePasswordLeastCommon(new Scanner(input))).isEqualTo("advent");
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2016/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.decodePasswordMostCommon(scanner)).isEqualTo("mlncjgdg");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2016/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.decodePasswordLeastCommon(scanner)).isEqualTo("bipjaytb");
        }
    }
}
