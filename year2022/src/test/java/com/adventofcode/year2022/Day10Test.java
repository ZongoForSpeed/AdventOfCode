package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2022, 10);
    }

    @Test
    void inputExample1() {
        String input = """
                noop
                addx 3
                addx -5""";

        Scanner scanner = new Scanner(input);
        long signalStrength = Day10.PartOne.getSignalStrength(scanner);
        Assertions.assertThat(signalStrength).isZero();
    }

    @Test
    void inputExample2() {
        String input = """
                addx 15
                addx -11
                addx 6
                addx -3
                addx 5
                addx -1
                addx -8
                addx 13
                addx 4
                noop
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx -35
                addx 1
                addx 24
                addx -19
                addx 1
                addx 16
                addx -11
                noop
                noop
                addx 21
                addx -15
                noop
                noop
                addx -3
                addx 9
                addx 1
                addx -3
                addx 8
                addx 1
                addx 5
                noop
                noop
                noop
                noop
                noop
                addx -36
                noop
                addx 1
                addx 7
                noop
                noop
                noop
                addx 2
                addx 6
                noop
                noop
                noop
                noop
                noop
                addx 1
                noop
                noop
                addx 7
                addx 1
                noop
                addx -13
                addx 13
                addx 7
                noop
                addx 1
                addx -33
                noop
                noop
                noop
                addx 2
                noop
                noop
                noop
                addx 8
                noop
                addx -1
                addx 2
                addx 1
                noop
                addx 17
                addx -9
                addx 1
                addx 1
                addx -3
                addx 11
                noop
                noop
                addx 1
                noop
                addx 1
                noop
                noop
                addx -13
                addx -19
                addx 1
                addx 3
                addx 26
                addx -30
                addx 12
                addx -1
                addx 3
                addx 1
                noop
                noop
                noop
                addx -9
                addx 18
                addx 1
                addx 2
                noop
                noop
                addx 9
                noop
                noop
                noop
                addx -1
                addx 2
                addx -37
                addx 1
                addx 3
                noop
                addx 15
                addx -21
                addx 22
                addx -6
                addx 1
                noop
                addx 2
                addx 1
                noop
                addx -10
                noop
                noop
                addx 20
                addx 1
                addx 2
                addx 2
                addx -6
                addx -11
                noop
                noop
                noop""";

        {
            Scanner scanner = new Scanner(input);
            long signalStrength = Day10.PartOne.getSignalStrength(scanner);
            Assertions.assertThat(signalStrength).isEqualTo(13140L);
        }

        {
            Scanner scanner = new Scanner(input);
            String crt = Day10.PartTwo.printCRT(scanner);
            Assertions.assertThat(crt).isEqualTo("""
                    ##..##..##..##..##..##..##..##..##..##..
                    ###...###...###...###...###...###...###.
                    ####....####....####....####....####....
                    #####.....#####.....#####.....#####.....
                    ######......######......######......####
                    #######.......#######.......#######.....""");
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        long signalStrength = Day10.PartOne.getSignalStrength(scanner);
        Assertions.assertThat(signalStrength).isEqualTo(14780L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        String crt = Day10.PartTwo.printCRT(scanner);
        Assertions.assertThat(crt).isEqualTo("""
                ####.#....###..#....####..##..####.#....
                #....#....#..#.#.......#.#..#....#.#....
                ###..#....#..#.#......#..#......#..#....
                #....#....###..#.....#...#.##..#...#....
                #....#....#....#....#....#..#.#....#....
                ####.####.#....####.####..###.####.####.""");
        // ELPLZGZL
    }

}
