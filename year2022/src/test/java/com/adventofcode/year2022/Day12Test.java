package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 12)
class Day12Test {

    @Test
    void inputExample() {
        var input = """
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi""";

        {
            var scanner = new Scanner(input);
            assertThat(Day12.partOne(scanner)).isEqualTo(31);
        }

        {
            var scanner = new Scanner(input);
            assertThat(Day12.partTwo(scanner)).isEqualTo(29);
        }

    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day12.partOne(scanner)).isEqualTo(517L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day12.partTwo(scanner)).isEqualTo(512L);
    }
}
