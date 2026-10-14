package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 8)
class Day08Test {

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
        int count = Day08.PartOne.countSteps(scanner);

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
        int count = Day08.PartOne.countSteps(scanner);

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

        long count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(6);
    }

    @Test
    void partOne(Scanner scanner) {
        int count = Day08.PartOne.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(22411);
    }

    @Test
    void partTwo(Scanner scanner) {
        long count = Day08.PartTwo.countSteps(scanner);
        Assertions.assertThat(count).isEqualTo(11188774513823L);
    }

}
