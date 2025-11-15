package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test extends AbstractTest {
    Day18Test() {
        super(2019, 18);
    }

    @Test
    void simple() {
        String input = """
                #########
                #b.A.@.a#
                #########""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(8);
        }
    }

    @Test
    void largerExample1() {
        String input = """
                ########################
                #f.D.E.e.C.b.A.@.a.B.c.#
                ######################.#
                #d.....................#
                ########################""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(86);
        }
    }

    @Test
    void largerExample2() {
        String input = """
                ########################
                #...............b.C.D.f#
                #.######################
                #.....@.a.B.c.d.A.e.F.g#
                ########################""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(132);
        }

    }

    @Test
    void largerExample3() {
        String input = """
                #################
                #i.G..c...e..H.p#
                ########.########
                #j.A..b...f..D.o#
                ########@########
                #k.E..a...g..B.n#
                ########.########
                #l.F..d...h..C.m#
                #################""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(136);
        }
    }

    @Test
    void largerExample4() {
        String input = """
                ########################
                #@..............ac.GI.b#
                ###d#e#f################
                ###A#B#C################
                ###g#h#i################
                ########################""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(81);
        }
    }

    @Test
    void simpleVault() {
        String input = """
                #######
                #a.#Cd#
                ##...##
                ##.@.##
                ##...##
                #cB#Ab#
                #######""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartTwo(scanner)).isEqualTo(8);
        }

    }

    @Test
    void largerVault() {
        String input = """
                ###############
                #d.ABC.#.....a#
                ######...######
                ######.@.######
                ######...######
                #b.....#.....c#
                ###############""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartTwo(scanner)).isEqualTo(24);
        }
    }

    @Test
    void complexVault1() {
        String input = """
                #############
                #DcBa.#.GhKl#
                #.###...#I###
                #e#d#.@.#j#k#
                ###C#...###J#
                #fEbA.#.FgHi#
                #############""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartTwo(scanner)).isEqualTo(32);
        }

    }

    @Test
    void complexVault2() {
        String input = """
                #############
                #g#f.D#..h#l#
                #F###e#E###.#
                #dCba...BcIJ#
                #####.@.#####
                #nK.L...G...#
                #M###N#H###.#
                #o#m..#i#jk.#
                #############""";
        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day18.algorithmPartTwo(scanner)).isEqualTo(70);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day18.algorithmPartOne(scanner)).isEqualTo(4590);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day18.algorithmPartTwo(scanner)).isEqualTo(2086);
    }
}
