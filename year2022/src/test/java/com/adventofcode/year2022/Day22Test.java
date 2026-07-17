package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test extends AbstractTest {

    Day22Test() {
        super(2022, 22);
    }

    @Test
    void example() {
        var input = """
                        ...#
                        .#..
                        #...
                        ....
                ...#.......#
                ........#...
                ..#....#....
                ..........#.
                        ...#....
                        .....#..
                        .#......
                        ......#.
                
                10R5L5R10L4R5L5""";

        try (var scanner = new Scanner(input)) {
            var password = Day22.partOne(scanner);
            assertThat(password).isEqualTo(6032);
        }

        try (var scanner = new Scanner(input)) {
            var password = Day22.partTwoExample(scanner);
            assertThat(password).isEqualTo(5031);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var monkeyMap = Day22.partOne(scanner);
        assertThat(monkeyMap).isEqualTo(117102);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var monkeyCube = Day22.partTwo(scanner);
        assertThat(monkeyCube).isEqualTo(135297);
    }

}
