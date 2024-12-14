package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day10Test extends AbstractTest {

    Day10Test() {
        super(2018, 10);
    }

    @Test
    void inputExample() {
        String input = """
                position=< 9,  1> velocity=< 0,  2>
                position=< 7,  0> velocity=<-1,  0>
                position=< 3, -2> velocity=<-1,  1>
                position=< 6, 10> velocity=<-2, -1>
                position=< 2, -4> velocity=< 2,  2>
                position=<-6, 10> velocity=< 2, -2>
                position=< 1,  8> velocity=< 1, -1>
                position=< 1,  7> velocity=< 1,  0>
                position=<-3, 11> velocity=< 1, -2>
                position=< 7,  6> velocity=<-1, -1>
                position=<-2,  3> velocity=< 1,  0>
                position=<-4,  3> velocity=< 2,  0>
                position=<10, -3> velocity=<-1,  1>
                position=< 5, 11> velocity=< 1, -2>
                position=< 4,  7> velocity=< 0, -1>
                position=< 8, -2> velocity=< 0,  1>
                position=<15,  0> velocity=<-2,  0>
                position=< 1,  6> velocity=< 1,  0>
                position=< 8,  9> velocity=< 0, -1>
                position=< 3,  3> velocity=<-1,  1>
                position=< 0,  5> velocity=< 0, -1>
                position=<-2,  2> velocity=< 2,  0>
                position=< 5, -2> velocity=< 1,  2>
                position=< 1,  4> velocity=< 2,  1>
                position=<-2,  7> velocity=< 2, -2>
                position=< 3,  6> velocity=<-1, -1>
                position=< 5,  0> velocity=< 1,  0>
                position=<-6,  0> velocity=< 2,  0>
                position=< 5,  9> velocity=< 1, -2>
                position=<14,  7> velocity=<-2,  0>
                position=<-3,  6> velocity=< 2, -1>""";
        Scanner scanner = new Scanner(input);

        Pair<String, Integer> result = Day10.decodeStars(scanner);
        assertThat(result.left()).isEqualTo("""
                #...#..###
                #...#...#.
                #...#...#.
                #####...#.
                #...#...#.
                #...#...#.
                #...#...#.
                #...#..###""");
        assertThat(result.right()).isEqualTo(3);
    }

    @Override
    public void partOne(Scanner scanner) {
        // TODO
    }

    @Override
    public void partTwo(Scanner scanner) {
        Pair<String, Integer> result = Day10.decodeStars(scanner);
        assertThat(result.left()).isEqualTo("""
                ######..#....#....##....#.......#.......#....#..#.......#####.
                #.......#...#....#..#...#.......#.......#...#...#.......#....#
                #.......#..#....#....#..#.......#.......#..#....#.......#....#
                #.......#.#.....#....#..#.......#.......#.#.....#.......#....#
                #####...##......#....#..#.......#.......##......#.......#####.
                #.......##......######..#.......#.......##......#.......#....#
                #.......#.#.....#....#..#.......#.......#.#.....#.......#....#
                #.......#..#....#....#..#.......#.......#..#....#.......#....#
                #.......#...#...#....#..#.......#.......#...#...#.......#....#
                ######..#....#..#....#..######..######..#....#..######..#####.""");
        assertThat(result.right()).isEqualTo(10227);
    }
}
