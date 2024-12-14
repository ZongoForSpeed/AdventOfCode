package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test extends AbstractTest {

    @Test
    void inputExample1() {
        String input = """
                p=2,4 v=2,-3""";

        try (Scanner scanner = new Scanner(input)) {
            Day14.partOne(scanner, 11, 7, 5, true);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                p=0,4 v=3,-3
                p=6,3 v=-1,-3
                p=10,3 v=-1,2
                p=2,0 v=2,-1
                p=0,0 v=1,3
                p=3,0 v=-2,-2
                p=7,6 v=-1,-3
                p=3,0 v=-1,-2
                p=9,3 v=2,3
                p=7,3 v=-1,2
                p=2,4 v=2,-3
                p=9,5 v=-3,-3""";

        try (Scanner scanner = new Scanner(input)) {
            assertThat(Day14.partOne(scanner, 11, 7, 100, true)).isEqualTo(12);
        }
    }


    protected Day14Test() {
        super(2024, 14);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day14.partOne(scanner, 101, 103, 100, false)).isEqualTo(229868730L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day14.partTwo(scanner, 101, 103)).isEqualTo(7861L);
    }

}
