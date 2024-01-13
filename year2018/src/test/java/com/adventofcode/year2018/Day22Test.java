package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day22Test {

    @Test
    void exampleInput() {

        String input = """
                depth: 510
                target: 10,10""";

        {
            Scanner scanner = new Scanner(input);
            int riskLevel = Day22.PartOne.computeRiskLevel(scanner);
            Assertions.assertThat(riskLevel).isEqualTo(114);
        }

        {
            Scanner scanner = new Scanner(input);
            int duration = Day22.PartTwo.findFastestWay(scanner);
            Assertions.assertThat(duration).isEqualTo(45);
        }
    }

    @Test
    void inputPartOne() {
        String input = """
                depth: 10647
                target: 7,770""";

        Scanner scanner = new Scanner(input);
        int riskLevel = Day22.PartOne.computeRiskLevel(scanner);
        Assertions.assertThat(riskLevel).isEqualTo(6208);
    }

    @Test
    void inputPartTwo() {
        String input = """
                depth: 10647
                target: 7,770""";

        Scanner scanner = new Scanner(input);
        int duration = Day22.PartTwo.findFastestWay(scanner);
        Assertions.assertThat(duration).isEqualTo(1039);
    }

}
