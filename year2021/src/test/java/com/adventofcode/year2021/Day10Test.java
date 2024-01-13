package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    @Test
    void inputExample() {
        String input = """
                [({(<(())[]>[[{[]{<()<>>
                [(()[<>])]({[<{<<[]>>(
                {([(<{}[<>[]}>{[]{[(<()>
                (((({<>}<{<{<>}{[]{[]{}
                [[<[([]))<([[{}[[()]]]
                [{[{({}]{}}([{[{{{}}([]
                {<[[]]>}<{[{[{[]{()[[[]
                [<(<(<(<{}))><([]([]()
                <{([([[(<>()){}]>(<<{{
                <{([{{}}[<[[[<>{}]]]>[]]""";

        assertThat(Day10.validateSyntax(new Scanner(input))).isEqualTo(26397);
        assertThat(Day10.completeSyntax(new Scanner(input))).isEqualTo(288957);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2021/day/10/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day10.validateSyntax(scanner)).isEqualTo(339411);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2021/day/10/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day10.completeSyntax(scanner)).isEqualTo(2289754624L);
        }
    }

}
