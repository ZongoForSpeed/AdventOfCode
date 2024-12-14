package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

class Day24Test extends AbstractTest {
    Day24Test() {
        super(2023, 24);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        List<Day24.Hailstorm> hailstorms = Day24.readHailstorms(scanner);

        int count = Day24.PartOne.countIntersection(hailstorms, 200000000000000L, 400000000000000L);

        Assertions.assertThat(count).isEqualTo(19976);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<Day24.Hailstorm> hailstorms = Day24.readHailstorms(scanner);

        long solved = Day24.PartTwo.solveEquation(hailstorms);
        Assertions.assertThat(solved).isEqualTo(849377770236905L);
        // 849377770236905
    }

}
