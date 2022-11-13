package com.adventofcode.year2018;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test {

    @Nested
    class PartOne {
        @Test
        void example1() {
            String input = """
                    #######
                    #.G...#
                    #...EG#
                    #.#.#G#
                    #..G#E#
                    #.....#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(27730);
        }

        @Test
        void example2() {
            String input = """
                    #######
                    #G..#E#
                    #E#E.E#
                    #G.##.#
                    #...#E#
                    #...E.#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(36334);
        }

        @Test
        void example3() {
            String input = """
                    #######
                    #E..EG#
                    #.#G.E#
                    #E.##E#
                    #G..#.#
                    #..E#.#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(39514);
        }

        @Test
        void example4() {
            String input = """
                    #######
                    #E.G#.#
                    #.#G..#
                    #G.#.G#
                    #G..#.#
                    #...E.#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(27755);
        }

        @Test
        void example5() {
            String input = """
                    #######
                    #.E...#
                    #.#..G#
                    #.###.#
                    #E#G#G#
                    #...#G#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(28944);
        }

        @Test
        void example6() {
            String input = """
                    #########
                    #G......#
                    #.E.#...#
                    #..##..G#
                    #...##..#
                    #...#...#
                    #.G...G.#
                    #.....G.#
                    #########""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(18740);
        }
    }

    @Nested
    class PartTwo {
        @Test
        void example1() {
            String input = """
                    #######
                    #.G...#
                    #...EG#
                    #.#.#G#
                    #..G#E#
                    #.....#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(4988);
        }

        @Test
        void example2() {
            String input = """
                    #######
                    #E..EG#
                    #.#G.E#
                    #E.##E#
                    #G..#.#
                    #..E#.#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(31284);
        }

        @Test
        void example3() {
            String input = """
                    #######
                    #E.G#.#
                    #.#G..#
                    #G.#.G#
                    #G..#.#
                    #...E.#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(3478);
        }

        @Test
        void example4() {
            String input = """
                    #######
                    #.E...#
                    #.#..G#
                    #.###.#
                    #E#G#G#
                    #...#G#
                    #######""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(6474);
        }

        @Test
        void example5() {
            String input = """
                    #########
                    #G......#
                    #.E.#...#
                    #..##..G#
                    #...##..#
                    #...#...#
                    #.G...G.#
                    #.....G.#
                    #########""";
            Scanner scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(1140);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2018/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day15.playPartOne(scanner)).hasValue(261855);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2018/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(59568);
        }
    }

}
