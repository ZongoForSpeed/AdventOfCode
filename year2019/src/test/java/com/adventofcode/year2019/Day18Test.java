package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test extends AbstractTest {
    Day18Test() {
        super(2019, 18);
    }

    @Test
    void testSimple() {
        String input = """
                #########
                #b.A.@.a#
                #########""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(8);
    }

    @Test
    void testLargerExample1() {
        String input = """
                ########################
                #f.D.E.e.C.b.A.@.a.B.c.#
                ######################.#
                #d.....................#
                ########################""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(86);
    }

    @Test
    void testLargerExample2() {
        String input = """
                ########################
                #...............b.C.D.f#
                #.######################
                #.....@.a.B.c.d.A.e.F.g#
                ########################""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(132);
    }

    @Test
    void testLargerExample3() {
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
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(136);
    }

    @Test
    void testLargerExample4() {
        String input = """
                ########################
                #@..............ac.GI.b#
                ###d#e#f################
                ###A#B#C################
                ###g#h#i################
                ########################""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(81);
    }

    @Test
    void testSimpleVault() {
        String input = """
                #######
                #a.#Cd#
                ##...##
                ##.@.##
                ##...##
                #cB#Ab#
                #######""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartTwo(lines)).isEqualTo(8);
    }

    @Test
    void testLargerVault() {
        String input = """
                ###############
                #d.ABC.#.....a#
                ######...######
                ######.@.######
                ######...######
                #b.....#.....c#
                ###############""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartTwo(lines)).isEqualTo(24);
    }

    @Test
    void testComplexVault1() {
        String input = """
                #############
                #DcBa.#.GhKl#
                #.###...#I###
                #e#d#.@.#j#k#
                ###C#...###J#
                #fEbA.#.FgHi#
                #############""";
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartTwo(lines)).isEqualTo(32);
    }

    @Test
    void testComplexVault2() {
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
        Stream<String> lines = Splitter.on('\n').splitToStream(input);
        assertThat(Day18.algorithmPartTwo(lines)).isEqualTo(70);
    }

    @Test
    void testInputPartTwo() throws IOException {
    }

    @Override
    public void partOne(Scanner scanner) {
        Stream<String> lines = FileUtils.readLines(scanner).stream();
        assertThat(Day18.algorithmPartOne(lines)).isEqualTo(4590);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Stream<String> lines = FileUtils.readLines(scanner).stream();
        assertThat(Day18.algorithmPartTwo(lines)).isEqualTo(2086);
    }
}
