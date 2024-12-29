package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test extends AbstractTest {
    Day20Test() {
        super(2019, 20);
    }

    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void testSimpleExample() {
        String input = """
                         A          \s
                         A          \s
                  #######.######### \s
                  #######.........# \s
                  #######.#######.# \s
                  #######.#######.# \s
                  #######.#######.# \s
                  #####  B    ###.# \s
                BC...##  C    ###.# \s
                  ##.##       ###.# \s
                  ##...DE  F  ###.# \s
                  #####    G  ###.# \s
                  #########.#####.# \s
                DE..#######...###.# \s
                  #.#########.###.# \s
                FG..#########.....# \s
                  ###########.##### \s
                             Z      \s
                             Z      \s""";
        try (Scanner scanner = new Scanner(input)) {
            long steps = Day20.solveMaze(scanner);
            assertThat(steps).isEqualTo(23);
        }
    }

    @Test
    @SuppressWarnings("MisleadingEscapedSpace")
    void testLargerExample() {
        String input = """
                                   A              \s
                                   A              \s
                  #################.############# \s
                  #.#...#...................#.#.# \s
                  #.#.#.###.###.###.#########.#.# \s
                  #.#.#.......#...#.....#.#.#...# \s
                  #.#########.###.#####.#.#.###.# \s
                  #.............#.#.....#.......# \s
                  ###.###########.###.#####.#.#.# \s
                  #.....#        A   C    #.#.#.# \s
                  #######        S   P    #####.# \s
                  #.#...#                 #......VT
                  #.#.#.#                 #.##### \s
                  #...#.#               YN....#.# \s
                  #.###.#                 #####.# \s
                DI....#.#                 #.....# \s
                  #####.#                 #.###.# \s
                ZZ......#               QG....#..AS
                  ###.###                 ####### \s
                JO..#.#.#                 #.....# \s
                  #.#.#.#                 ###.#.# \s
                  #...#..DI             BU....#..LF
                  #####.#                 #.##### \s
                YN......#               VT..#....QG
                  #.###.#                 #.###.# \s
                  #.#...#                 #.....# \s
                  ###.###    J L     J    #.#.### \s
                  #.....#    O F     P    #.#...# \s
                  #.###.#####.#.#####.#####.###.# \s
                  #...#.#.#...#.....#.....#.#...# \s
                  #.#####.###.###.#.#.#########.# \s
                  #...#.#.....#...#.#.#.#.....#.# \s
                  #.###.#####.###.###.#.#.####### \s
                  #.#.........#...#.............# \s
                  #########.###.###.############# \s
                           B   J   C              \s
                           U   P   P              \s""";
        try (Scanner scanner = new Scanner(input)) {
            long steps = Day20.solveMaze(scanner);
            assertThat(steps).isEqualTo(58);
        }

    }

    @SuppressWarnings("MisleadingEscapedSpace")
    @Test
    void testRecursiveDonutMaze() {
        String input = """
                             Z L X W       C                \s
                             Z P Q B       K                \s
                  ###########.#.#.#.#######.############### \s
                  #...#.......#.#.......#.#.......#.#.#...# \s
                  ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.### \s
                  #.#...#.#.#...#.#.#...#...#...#.#.......# \s
                  #.###.#######.###.###.#.###.###.#.####### \s
                  #...#.......#.#...#...#.............#...# \s
                  #.#########.#######.#.#######.#######.### \s
                  #...#.#    F       R I       Z    #.#.#.# \s
                  #.###.#    D       E C       H    #.#.#.# \s
                  #.#...#                           #...#.# \s
                  #.###.#                           #.###.# \s
                  #.#....OA                       WB..#.#..ZH
                  #.###.#                           #.#.#.# \s
                CJ......#                           #.....# \s
                  #######                           ####### \s
                  #.#....CK                         #......IC
                  #.###.#                           #.###.# \s
                  #.....#                           #...#.# \s
                  ###.###                           #.#.#.# \s
                XF....#.#                         RF..#.#.# \s
                  #####.#                           ####### \s
                  #......CJ                       NM..#...# \s
                  ###.#.#                           #.###.# \s
                RE....#.#                           #......RF
                  ###.###        X   X       L      #.#.#.# \s
                  #.....#        F   Q       P      #.#.#.# \s
                  ###.###########.###.#######.#########.### \s
                  #.....#...#.....#.......#...#.....#.#...# \s
                  #####.#.###.#######.#######.###.###.#.#.# \s
                  #.......#.......#.#.#.#.#...#...#...#.#.# \s
                  #####.###.#####.#.#.#.#.###.###.#.###.### \s
                  #.......#.....#.#...#...............#...# \s
                  #############.#.#.###.################### \s
                               A O F   N                    \s
                               A A D   M                    \s""";
        try (Scanner scanner = new Scanner(input)) {
            long steps = Day20.solveRecursiveMaze(scanner);
            assertThat(steps).isEqualTo(396);
        }

    }

    @Override
    public void partOne(Scanner scanner) {
        long steps = Day20.solveMaze(scanner);
        assertThat(steps).isEqualTo(684);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long steps = Day20.solveRecursiveMaze(scanner);
        assertThat(steps).isEqualTo(7758L);
    }
}
