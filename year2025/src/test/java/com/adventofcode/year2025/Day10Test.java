package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 10)
public class Day10Test {

    @Test
    void inputExample() {
        var input = """
                [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""";


        try (var scanner = new Scanner(input)) {
            long sum = Day10.factoryPartOne(scanner);
            assertThat(sum).isEqualTo(7);
        }

        try (var scanner = new Scanner(input)) {
            long sum = Day10.factoryPartTwo(scanner);
            assertThat(sum).isEqualTo(33);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long sum = Day10.factoryPartOne(scanner);
        assertThat(sum).isEqualTo(486);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long sum = Day10.factoryPartTwo(scanner);
        assertThat(sum).isEqualTo(17820);
    }
}
