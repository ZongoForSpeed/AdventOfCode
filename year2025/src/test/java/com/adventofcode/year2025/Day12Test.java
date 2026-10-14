package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 12)
public class Day12Test {

    @Test
    void inputExample() {
        var input = """
                0:
                ###
                ##.
                ##.
                
                1:
                ###
                ##.
                .##
                
                2:
                .##
                ###
                ##.
                
                3:
                ##.
                ###
                ##.
                
                4:
                ###
                #..
                ###
                
                5:
                ###
                .#.
                ###
                
                4x4: 0 0 0 0 2 0
                12x5: 1 0 1 0 2 2
                12x5: 1 0 1 0 3 2""";

        try (var scanner = new Scanner(input)) {
            long solution = Day12.christmasTreeFarm(scanner);
            assertThat(solution).isEqualTo(3);
        }

    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long solution = Day12.christmasTreeFarm(scanner);
        assertThat(solution).isEqualTo(531);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
    }

}
