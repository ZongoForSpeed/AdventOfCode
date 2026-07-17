package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test extends AbstractTest {

    protected Day08Test() {
        super(2024, 8);
    }

    @Test
    void inputExample() {
        var input = """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............""";

        try (var scanner = new Scanner(input)) {
            var antinodes = Day08.partOne(scanner);
            assertThat(antinodes).hasSize(14);
        }

        try (var scanner = new Scanner(input)) {
            var antinodes = Day08.partTwo(scanner);
            assertThat(antinodes).hasSize(34);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day08.partOne(scanner)).hasSize(394);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day08.partTwo(scanner)).hasSize(1277);
    }

}
