package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day08Test extends AbstractTest {

    Day08Test() {
        super(2023, 8);
    }

    @Test
    void inputExample1() {
        String input = """
                RL
                
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)""";

        Scanner scanner = new Scanner(input);
        int count = Day08.PartOne.countSteps(scanner);

        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void inputExample2() {
        String input = """
                LLR
                
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)""";

        Scanner scanner = new Scanner(input);
        int count = Day08.PartOne.countSteps(scanner);

        Assertions.assertThat(count).isEqualTo(6);
    }

    @Test
    void inputExample3() {
        String input = """
                LR
                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)""";

        Scanner scanner = new Scanner(input);

        long count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(6);
    }

    @Override
    public void partOne(Scanner scanner) {
        int count = Day08.PartOne.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(22411);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(11188774513823L);
    }

}
