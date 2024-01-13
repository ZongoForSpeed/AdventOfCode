package com.adventofcode.year2017;

import com.adventofcode.point.map.BooleanMap;
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
                ../.# => ##./#../...
                .#./..#/### => #..#/..../..../#..#""";

        Scanner scanner = new Scanner(input);

        assertThat(Day21.buildFractal(scanner, 2)).extracting(BooleanMap::cardinality).isEqualTo(12L);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day21.buildFractal(scanner, 5)).extracting(BooleanMap::cardinality).isEqualTo(152L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day21.buildFractal(scanner, 18)).extracting(BooleanMap::cardinality).isEqualTo(1956174L);
        }
    }

}
