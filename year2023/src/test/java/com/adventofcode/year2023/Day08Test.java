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
        var input = """
                RL
                
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)""";

        var scanner = new Scanner(input);
        var count = Day08.PartOne.countSteps(scanner);

        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void inputExample2() {
        var input = """
                LLR
                
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)""";

        var scanner = new Scanner(input);
        var count = Day08.PartOne.countSteps(scanner);

        Assertions.assertThat(count).isEqualTo(6);
    }

    @Test
    void inputExample3() {
        var input = """
                LR
                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)""";

        var scanner = new Scanner(input);

        var count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(6);
    }

    @Override
    public void partOne(Scanner scanner) {
        var count = Day08.PartOne.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(22411);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(11188774513823L);
    }

}
