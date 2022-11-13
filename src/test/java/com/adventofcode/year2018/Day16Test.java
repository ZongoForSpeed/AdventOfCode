package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test {

    @Test
    void example() {
        String input = """
                Before: [3, 2, 1, 1]
                9 2 1 2
                After:  [3, 2, 2, 1]
                                
                """;
        Scanner scanner = new Scanner(input);
        assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(1);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(580);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/16/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day16.chronalClassificationPartTwo(scanner)).isEqualTo(537);
        }
    }

}
