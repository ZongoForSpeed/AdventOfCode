package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2017/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.getBottomProgram(scanner)).hasSize(1).contains("vvsvez");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2017/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.balanceWeight(scanner)).isPresent().hasValue(362);
        }
    }

}
