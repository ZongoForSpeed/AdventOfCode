package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day22Test {

    @Test
    void exampleInput() {

        var input = """
                depth: 510
                target: 10,10""";

        {
            var scanner = new Scanner(input);
            var riskLevel = Day22.PartOne.computeRiskLevel(scanner);
            Assertions.assertThat(riskLevel).isEqualTo(114);
        }

        {
            var scanner = new Scanner(input);
            var duration = Day22.PartTwo.findFastestWay(scanner);
            Assertions.assertThat(duration).isEqualTo(45);
        }
    }

    @Test
    void inputPartOne() {
        var input = """
                depth: 10647
                target: 7,770""";

        var scanner = new Scanner(input);
        var riskLevel = Day22.PartOne.computeRiskLevel(scanner);
        Assertions.assertThat(riskLevel).isEqualTo(6208);
    }

    @Test
    void inputPartTwo() {
        var input = """
                depth: 10647
                target: 7,770""";

        var scanner = new Scanner(input);
        var duration = Day22.PartTwo.findFastestWay(scanner);
        Assertions.assertThat(duration).isEqualTo(1039);
    }

}
