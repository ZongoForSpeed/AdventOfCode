package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Day24Test {

    @Test
    void inputExample() {
        String input = """
                19, 13, 30 @ -2,  1, -2
                18, 19, 22 @ -1, -1, -2
                20, 25, 34 @ -2, -2, -4
                12, 31, 28 @ -1, -2, -1
                20, 19, 15 @  1, -5, -3""";

        Scanner scanner = new Scanner(input);
        List<Day24.Hailstorm> hailstorms = Day24.readHailstorms(scanner);
        int count = Day24.PartOne.countIntersection(hailstorms, 7, 27);
        Assertions.assertThat(count).isEqualTo(2);

        long solved = Day24.PartTwo.solveEquation(hailstorms);
        Assertions.assertThat(solved).isEqualTo(47);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day24Test.class.getResourceAsStream("/2023/day/24/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            List<Day24.Hailstorm> hailstorms = Day24.readHailstorms(scanner);

            int count = Day24.PartOne.countIntersection(hailstorms, 200000000000000L, 400000000000000L);

            Assertions.assertThat(count).isEqualTo(19976);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day24Test.class.getResourceAsStream("/2023/day/24/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            List<Day24.Hailstorm> hailstorms = Day24.readHailstorms(scanner);

            long solved = Day24.PartTwo.solveEquation(hailstorms);
            Assertions.assertThat(solved).isEqualTo(849377770236905L);
            // 849377770236905
        }
    }

}
