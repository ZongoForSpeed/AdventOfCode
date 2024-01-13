package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    @Test
    void inputExample() {
        String input = """
                NNCB

                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C""";


        assertThat(Day14.extendedPolymerization(new Scanner(input), 10)).isEqualTo(1588);
        assertThat(Day14.extendedPolymerization(new Scanner(input), 40)).isEqualTo(2188189693529L);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2021/day/14/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day14.extendedPolymerization(scanner, 10)).isEqualTo(3411);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2021/day/14/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day14.extendedPolymerization(scanner, 40)).isEqualTo(7477815755570L);
        }
    }

}
