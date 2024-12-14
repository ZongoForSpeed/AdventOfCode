package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2023, 10);
    }

    @Test
    void inputExample1() {
        String input = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....""";

        Scanner scanner = new Scanner(input);
        int depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(4);
    }

    @Test
    void inputExample2() {
        String input = """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...""";

        Scanner scanner = new Scanner(input);
        int depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(8);
    }

    @Test
    void inputExample3() {
        String input = """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........""";

        Scanner scanner = new Scanner(input);
        int count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(4);
    }

    @Test
    void inputExample4() {
        String input = """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...""";

        Scanner scanner = new Scanner(input);
        int count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(8);
    }

    @Test
    void inputExample5() {
        String input = """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L""";

        Scanner scanner = new Scanner(input);
        int count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        int depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(6613);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int depth = Day10.PartTwo.countEnclosedTiles(scanner);
        Assertions.assertThat(depth).isEqualTo(511);
    }


}
