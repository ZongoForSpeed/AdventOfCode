package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 7)
class Day07Test {
    @Test
    void inputExample() {
        var input = """
                pbga (66)
                xhth (57)
                ebii (61)
                havc (66)
                ktlj (57)
                fwft (72) -> ktlj, cntj, xhth
                qoyq (66)
                padx (45) -> pbga, havc, qoyq
                tknk (41) -> ugml, padx, fwft
                jptl (61)
                ugml (68) -> gyxo, ebii, jptl
                gyxo (61)
                cntj (57)""";

        assertThat(Day07.getBottomProgram(new Scanner(input))).hasSize(1).contains("tknk");

        assertThat(Day07.balanceWeight(new Scanner(input))).isPresent().hasValue(60);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day07.getBottomProgram(scanner)).hasSize(1).contains("vvsvez");
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day07.balanceWeight(scanner)).isPresent().hasValue(362);
    }

}
