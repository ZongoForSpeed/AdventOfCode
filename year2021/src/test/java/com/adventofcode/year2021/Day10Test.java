package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2021, 10);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day10.validateSyntax(scanner)).isEqualTo(339411);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day10.completeSyntax(scanner)).isEqualTo(2289754624L);
    }

}
