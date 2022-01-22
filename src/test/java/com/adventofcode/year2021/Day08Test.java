package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {
    private final Day08 day08;

    Day08Test() {
        day08 = new Day08();
    }

    @Test
    void inputExample() {
        String input = """
                be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
                edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
                fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
                fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
                aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
                fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
                dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
                bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
                egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
                gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""";

        assertThat(day08.count1478(new Scanner(input))).isEqualTo(26);

        assertThat(day08.solveMapping("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")).isEqualTo(5353);

        Scanner scanner = new Scanner(input);

        assertThat(day08.solveMapping(scanner)).isEqualTo(61229);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2021/day/8/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(day08.count1478(scanner)).isEqualTo(355);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2021/day/8/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(day08.solveMapping(scanner)).isEqualTo(983030);
        }
    }
}
