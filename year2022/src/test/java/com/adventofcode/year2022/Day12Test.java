package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test extends AbstractTest {

    Day12Test() {
        super(2022, 12);
    }

    @Test
    void inputExample() {
        String input = """
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day12.partOne(scanner)).isEqualTo(31);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day12.partTwo(scanner)).isEqualTo(29);
        }

    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day12.partOne(scanner)).isEqualTo(517L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day12.partTwo(scanner)).isEqualTo(512L);
    }
}
