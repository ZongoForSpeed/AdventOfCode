package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test extends AbstractTest {
    Day18Test() {
        super(2022, 18);
    }

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
            var surface = Day18.computeSurfaceArea(scanner);
            assertThat(surface).isEqualTo(64);
        }

        {
            var scanner = new Scanner(input);
            var surface = Day18.exteriorSurfaceArea(scanner);
            assertThat(surface).isEqualTo(58);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day18.computeSurfaceArea(scanner)).isEqualTo(4310);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day18.exteriorSurfaceArea(scanner)).isEqualTo(2466);
    }

}
