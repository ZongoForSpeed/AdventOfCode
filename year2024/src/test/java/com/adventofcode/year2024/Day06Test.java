package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test extends AbstractTest {

    protected Day06Test() {
        super(2024, 6);
    }

    @Test
    void inputExample() {
        String input = """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...""";

        try (Scanner scanner = new Scanner(input)) {
            int path = Day06.partOne(scanner);
            assertThat(path).isEqualTo(41);
        }

        try (Scanner scanner = new Scanner(input)) {
            int count = Day06.partTwo(scanner);
            assertThat(count).isEqualTo(6);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.partOne(scanner)).isEqualTo(4752);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.partTwo(scanner)).isEqualTo(1719);
    }

}
