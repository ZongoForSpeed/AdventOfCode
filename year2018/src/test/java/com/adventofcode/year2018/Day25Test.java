package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day25Test {

    @Test
    void inputExample1() {
        String input = """
                 0,0,0,0
                 3,0,0,0
                 0,3,0,0
                 0,0,3,0
                 0,0,0,3
                 0,0,0,6
                 9,0,0,0
                12,0,0,0""";

        Scanner scanner = new Scanner(input);
        int size = Day25.countConstellations(scanner);
        Assertions.assertThat(size).isEqualTo(2);
    }

    @Test
    void inputExample2() {
        String input = """
                -1,2,2,0
                0,0,2,-2
                0,0,0,-2
                -1,2,0,0
                -2,-2,-2,2
                3,0,2,-1
                -1,3,2,2
                -1,0,-1,0
                0,2,1,-2
                3,0,0,0""";

        Scanner scanner = new Scanner(input);
        int size = Day25.countConstellations(scanner);
        Assertions.assertThat(size).isEqualTo(4);
    }

    @Test
    void inputExample3() {
        String input = """
                1,-1,0,1
                2,0,-1,0
                3,2,-1,0
                0,0,3,1
                0,0,-1,-1
                2,3,-2,0
                -2,2,0,0
                2,-2,0,-1
                1,-1,0,-1
                3,2,0,2""";

        Scanner scanner = new Scanner(input);
        int size = Day25.countConstellations(scanner);
        Assertions.assertThat(size).isEqualTo(3);
    }

    @Test
    void inputExample4() {
        String input = """
                1,-1,-1,-2
                -2,-2,0,1
                0,2,1,3
                -2,3,-2,1
                0,2,3,-2
                -1,-1,1,-2
                0,-2,-1,0
                -2,2,3,-1
                1,2,2,0
                -1,-2,0,-2""";

        Scanner scanner = new Scanner(input);
        int size = Day25.countConstellations(scanner);
        Assertions.assertThat(size).isEqualTo(8);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day25Test.class.getResourceAsStream("/2018/day/25/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {

            int size = Day25.countConstellations(scanner);
            Assertions.assertThat(size).isEqualTo(428);
        }
    }
}
