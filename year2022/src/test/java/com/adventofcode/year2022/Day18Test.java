package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 18)
class Day18Test {
    @Test
    void inputExample() {
        var input = """
                2,2,2
                1,2,2
                3,2,2
                2,1,2
                2,3,2
                2,2,1
                2,2,3
                2,2,4
                2,2,6
                1,2,5
                3,2,5
                2,1,5
                2,3,5""";

        {
            var scanner = new Scanner(input);
            long surface = Day18.computeSurfaceArea(scanner);
            assertThat(surface).isEqualTo(64);
        }

        {
            var scanner = new Scanner(input);
            long surface = Day18.exteriorSurfaceArea(scanner);
            assertThat(surface).isEqualTo(58);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day18.computeSurfaceArea(scanner)).isEqualTo(4310);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day18.exteriorSurfaceArea(scanner)).isEqualTo(2466);
    }

}
