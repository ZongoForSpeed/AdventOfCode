package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 24)
class Day24Test {
    @Test
    void inputExample() {
        var input = """
                19, 13, 30 @ -2,  1, -2
                18, 19, 22 @ -1, -1, -2
                20, 25, 34 @ -2, -2, -4
                12, 31, 28 @ -1, -2, -1
                20, 19, 15 @  1, -5, -3""";

        var scanner = new Scanner(input);
        var hailstorms = Day24.readHailstorms(scanner);
        int count = Day24.PartOne.countIntersection(hailstorms, 7, 27);
        Assertions.assertThat(count).isEqualTo(2);

        long solved = Day24.PartTwo.solveEquation(hailstorms);
        Assertions.assertThat(solved).isEqualTo(47);
    }

    @Test
    void partOne(Scanner scanner) {
        var hailstorms = Day24.readHailstorms(scanner);

        int count = Day24.PartOne.countIntersection(hailstorms, 200000000000000L, 400000000000000L);

        Assertions.assertThat(count).isEqualTo(19976);
    }

    @Test
    void partTwo(Scanner scanner) {
        var hailstorms = Day24.readHailstorms(scanner);

        long solved = Day24.PartTwo.solveEquation(hailstorms);
        Assertions.assertThat(solved).isEqualTo(849377770236905L);
        // 849377770236905
    }

}
