package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;





import java.util.Scanner;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2023, 10);
    }

    @Test
    void inputExample1() {
        var input = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....""";

        var scanner = new Scanner(input);
        var depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(4);
    }

    @Test
    void inputExample2() {
        var input = """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...""";

        var scanner = new Scanner(input);
        var depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(8);
    }

    @Test
    void inputExample3() {
        var input = """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........""";

        var scanner = new Scanner(input);
        var count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(4);
    }

    @Test
    void inputExample4() {
        var input = """
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

        var scanner = new Scanner(input);
        var count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(8);
    }

    @Test
    void inputExample5() {
        var input = """
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

        var scanner = new Scanner(input);
        var count = Day10.PartTwo.countEnclosedTiles(scanner);

        Assertions.assertThat(count).isEqualTo(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        var depth = Day10.PartOne.findMaxDepth(scanner);
        Assertions.assertThat(depth).isEqualTo(6613);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var depth = Day10.PartTwo.countEnclosedTiles(scanner);
        Assertions.assertThat(depth).isEqualTo(511);
    }


}
