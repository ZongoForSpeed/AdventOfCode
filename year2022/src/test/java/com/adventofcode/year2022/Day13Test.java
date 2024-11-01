package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void inputExample() {
        String input = """
                [1,1,3,1,1]
                [1,1,5,1,1]
                                
                [[1],[2,3,4]]
                [[1],4]
                                
                [9]
                [[8,7,6]]
                                
                [[4,4],4,4]
                [[4,4],4,4,4]
                                
                [7,7,7,7]
                [7,7,7]
                                
                []
                [3]
                                
                [[[]]]
                [[]]
                                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day13.partOne(scanner)).isEqualTo(13);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day13.partTwo(scanner)).isEqualTo(140);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day13Test.class.getResourceAsStream("/2022/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day13.partOne(scanner)).isEqualTo(6235);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day13Test.class.getResourceAsStream("/2022/day/13/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day13.partTwo(scanner)).isEqualTo(22866);
        }
    }
}
