package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day15Test extends AbstractTest {
    Day15Test() {
        super(2018, 15);
    }

    @Nested
    class PartOne {
        @Test
        void example1() {
            var input = """
                    #######
                    #.G...#
                    #...EG#
                    #.#.#G#
                    #..G#E#
                    #.....#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(27730);
        }

        @Test
        void example2() {
            var input = """
                    #######
                    #G..#E#
                    #E#E.E#
                    #G.##.#
                    #...#E#
                    #...E.#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(36334);
        }

        @Test
        void example3() {
            var input = """
                    #######
                    #E..EG#
                    #.#G.E#
                    #E.##E#
                    #G..#.#
                    #..E#.#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(39514);
        }

        @Test
        void example4() {
            var input = """
                    #######
                    #E.G#.#
                    #.#G..#
                    #G.#.G#
                    #G..#.#
                    #...E.#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(27755);
        }

        @Test
        void example5() {
            var input = """
                    #######
                    #.E...#
                    #.#..G#
                    #.###.#
                    #E#G#G#
                    #...#G#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(28944);
        }

        @Test
        void example6() {
            var input = """
                    #########
                    #G......#
                    #.E.#...#
                    #..##..G#
                    #...##..#
                    #...#...#
                    #.G...G.#
                    #.....G.#
                    #########""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartOne(scanner)).hasValue(18740);
        }
    }

    @Nested
    class PartTwo {
        @Test
        void example1() {
            var input = """
                    #######
                    #.G...#
                    #...EG#
                    #.#.#G#
                    #..G#E#
                    #.....#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(4988);
        }

        @Test
        void example2() {
            var input = """
                    #######
                    #E..EG#
                    #.#G.E#
                    #E.##E#
                    #G..#.#
                    #..E#.#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(31284);
        }

        @Test
        void example3() {
            var input = """
                    #######
                    #E.G#.#
                    #.#G..#
                    #G.#.G#
                    #G..#.#
                    #...E.#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(3478);
        }

        @Test
        void example4() {
            var input = """
                    #######
                    #.E...#
                    #.#..G#
                    #.###.#
                    #E#G#G#
                    #...#G#
                    #######""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(6474);
        }

        @Test
        void example5() {
            var input = """
                    #########
                    #G......#
                    #.E.#...#
                    #..##..G#
                    #...##..#
                    #...#...#
                    #.G...G.#
                    #.....G.#
                    #########""";
            var scanner = new Scanner(input);
            assertThat(Day15.playPartTwo(scanner)).isEqualTo(1140);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day15.playPartOne(scanner)).hasValue(261855);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day15.playPartTwo(scanner)).isEqualTo(59568);
    }

}
