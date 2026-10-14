package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 10)
class Day10Test {
    @Test
    void inputExample() {
        var input = """
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
    void partOne(Scanner scanner) {
        assertThat(Day10.validateSyntax(scanner)).isEqualTo(339411);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day10.completeSyntax(scanner)).isEqualTo(2289754624L);
    }

}
