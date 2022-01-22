package com.adventofcode.year2015;

import com.adventofcode.utils.IntegerPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day09Test {

    @Test
    void inputExample() {
        String input = """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141""";

        Scanner scanner = new Scanner(input);

        IntegerPair bestDistance = Day09.computeBestDistance(scanner);
        assertThat(bestDistance.left()).isEqualTo(605);
        assertThat(bestDistance.right()).isEqualTo(982);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2015/day/9/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day09.computeBestDistance(scanner).left()).isEqualTo(141);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2015/day/9/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day09.computeBestDistance(scanner).right()).isEqualTo(736);
        }
    }
}
