package com.adventofcode.year2018;

import org.apache.commons.lang3.tuple.Pair;
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
        assertThat(result.getLeft()).isEqualTo("""
                #...#..###
                #...#...#.
                #...#...#.
                #####...#.
                #...#...#.
                #...#...#.
                #...#...#.
                #...#..###""");
        assertThat(result.getRight()).isEqualTo(3);
    }

    @Test
    void input() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2018/day/10/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            Pair<String, Integer> result = Day10.decodeStars(scanner);
            assertThat(result.getLeft()).isEqualTo("""
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
            assertThat(result.getRight()).isEqualTo(10227);
        }
    }

}
