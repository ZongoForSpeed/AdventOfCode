package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
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
        long steps = Day20.solveMaze(Arrays.stream(input.split("\\n")));
        assertThat(steps).isEqualTo(23);
    }


    @Test
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
        long steps = Day20.solveMaze(Arrays.stream(input.split("\\n")));
        assertThat(steps).isEqualTo(58);
    }

    @Test
    void testInputPartOne() throws IOException {
        long steps = Day20.solveMaze(FileUtils.readLines("/2019/day/20/input").stream());
        assertThat(steps).isEqualTo(684);
    }

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
        Stream<String> stream = Arrays.stream(input.split("\\n"));
        long steps = Day20.solveRecursiveMaze(stream);
        assertThat(steps).isEqualTo(396);
    }

    @Test
    void testInputPartTwo() throws IOException {
        Stream<String> stream = FileUtils.readLines("/2019/day/20/input").stream();
        assertThat(Day20.solveRecursiveMaze(stream)).isEqualTo(7758L);
    }

}
