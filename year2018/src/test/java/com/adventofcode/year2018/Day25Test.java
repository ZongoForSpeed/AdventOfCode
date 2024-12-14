package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day25Test extends AbstractTest {

    Day25Test() {
        super(2018, 25);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        int size = Day25.countConstellations(scanner);
        Assertions.assertThat(size).isEqualTo(428);
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }
}
