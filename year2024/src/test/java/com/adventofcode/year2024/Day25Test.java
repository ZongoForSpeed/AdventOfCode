package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test extends AbstractTest {

    protected Day25Test() {
        super(2024, 25);
    }

    @Test
    void inputExample() {
        var input = """
                #####
                .####
                .####
                .####
                .#.#.
                .#...
                .....
                
                #####
                ##.##
                .#.##
                ...##
                ...#.
                ...#.
                .....
                
                .....
                #....
                #....
                #...#
                #.#.#
                #.###
                #####
                
                .....
                .....
                #.#..
                ###..
                ###.#
                ###.#
                #####
                
                .....
                .....
                .....
                #....
                #.#..
                #.#.#
                #####""";

        try (var scanner = new Scanner(input)) {
            var count = Day25.partOne(scanner);
            assertThat(count).isEqualTo(3);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        var count = Day25.partOne(scanner);
        assertThat(count).isEqualTo(3155);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        Day25.partTwo(scanner);
    }

}
