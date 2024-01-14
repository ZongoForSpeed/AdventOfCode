package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day23Test {

    @Test
    void inputExample1() {
        String input = """
                pos=<0,0,0>, r=4
                pos=<1,0,0>, r=1
                pos=<4,0,0>, r=3
                pos=<0,2,0>, r=1
                pos=<0,5,0>, r=3
                pos=<0,0,3>, r=1
                pos=<1,1,1>, r=1
                pos=<1,1,2>, r=1
                pos=<1,3,1>, r=1""";

        Scanner scanner = new Scanner(input);
        long count = Day23.PartOne.countNanobots(scanner);

        Assertions.assertThat(count).isEqualTo(7);
    }

    @Test
    void inputExample2() {
        String input = """
                pos=<10,12,12>, r=2
                pos=<12,14,12>, r=2
                pos=<16,12,12>, r=4
                pos=<14,14,14>, r=6
                pos=<50,50,50>, r=200
                pos=<10,10,10>, r=5""";

        Scanner scanner = new Scanner(input);
        int distance = Day23.PartTwo.bestPositionDistance(scanner);

        Assertions.assertThat(distance).isEqualTo(36);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2018/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long count = Day23.PartOne.countNanobots(scanner);

            Assertions.assertThat(count).isEqualTo(943);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2018/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int distance = Day23.PartTwo.bestPositionDistance(scanner);

            Assertions.assertThat(distance).isEqualTo(84087816);
        }
    }

}
