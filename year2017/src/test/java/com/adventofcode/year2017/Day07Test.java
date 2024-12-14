package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test extends AbstractTest {
    Day07Test() {
        super(2017, 7);
    }

    @Test
    void inputExample() {
        String input = """
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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day07.getBottomProgram(scanner)).hasSize(1).contains("vvsvez");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day07.balanceWeight(scanner)).isPresent().hasValue(362);
    }

}
