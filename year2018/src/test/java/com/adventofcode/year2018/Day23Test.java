package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day23Test extends AbstractTest {
    Day23Test() {
        super(2018, 23);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        long count = Day23.PartOne.countNanobots(scanner);
        Assertions.assertThat(count).isEqualTo(943);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int distance = Day23.PartTwo.bestPositionDistance(scanner);
        Assertions.assertThat(distance).isEqualTo(84087816);
    }

}
