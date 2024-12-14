package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day09Test extends AbstractTest {
    Day09Test() {
        super(2022, 9);
    }

    @Test
    void inputExample1() {
        String input = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2""";

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartOne.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(13);
        }

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(1);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20""";

        {
            Scanner scanner = new Scanner(input);
            int size = Day09.PartTwo.countTailPositions(scanner);
            Assertions.assertThat(size).isEqualTo(36);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        int size = Day09.PartOne.countTailPositions(scanner);
        Assertions.assertThat(size).isEqualTo(5858);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int size = Day09.PartTwo.countTailPositions(scanner);
        Assertions.assertThat(size).isEqualTo(2602);
    }

}
