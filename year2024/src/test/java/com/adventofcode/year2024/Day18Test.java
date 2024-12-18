package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test extends AbstractTest {

    Day18Test() {
        super(2024, 18);
    }

    @Test
    void inputExample() {
        String input = """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0""";

        try (Scanner scanner = new Scanner(input)) {
            long path = Day18.partOne(scanner, 6, 12);
            assertThat(path).isEqualTo(22);
        }

        try (Scanner scanner = new Scanner(input)) {
            String memory = Day18.partTwo(scanner, 6);
            assertThat(memory).isEqualTo("6,1");
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        long path = Day18.partOne(scanner, 70, 1024);
        assertThat(path).isEqualTo(438L);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        String memory = Day18.partTwo(scanner, 70);
        assertThat(memory).isEqualTo("26,22");
    }

}
