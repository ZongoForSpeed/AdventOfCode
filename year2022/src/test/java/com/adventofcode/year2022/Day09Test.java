package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2022, day = 9)
class Day09Test {
    @Test
    void inputExample1() {
        var input = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2""";

        {
            var scanner = new Scanner(input);
            int size = Day09.PartOne.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(13);
        }

        {
            var scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(1);
        }
    }

    @Test
    void inputExample2() {
        var input = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20""";

        {
            var scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(36);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int size = Day09.PartOne.countTailPositions(scanner);
        Assertions.assertThat(size).isEqualTo(5858);
    }

    @Test
    void partTwo(Scanner scanner) {
        int size = Day09.PartTwo.countTailPositions(scanner);
        Assertions.assertThat(size).isEqualTo(2602);
    }

}
