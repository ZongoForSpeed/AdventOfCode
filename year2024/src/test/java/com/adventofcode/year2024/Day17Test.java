package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 17)
class Day17Test {

    @Test
    void example1() {
        var input = """
                Register A: 729
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0""";

        try (var scanner = new Scanner(input)) {
            String join = Day17.partOne(scanner);
            assertThat(join).isEqualTo("4,6,3,5,6,3,5,2,1,0");
        }
    }

    @Test
    void example2() {
        var input = """
                Register A: 2024
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0""";

        try (var scanner = new Scanner(input)) {
            String join = Day17.partOne(scanner);
            assertThat(join).isEqualTo("4,2,5,6,7,7,7,7,3,1,0");
        }
    }

    @Test
    void example3() {
        var input = """
                Register A: 2024
                Register B: 0
                Register C: 0
                
                Program: 0,3,5,4,3,0""";

        try (var scanner = new Scanner(input)) {
            long register = Day17.partTwoBruteForce(scanner);
            assertThat(register).isEqualTo(117440);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        String join = Day17.partOne(scanner);
        assertThat(join).isEqualTo("3,6,3,7,0,7,0,3,0");
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long register = Day17.partTwo(scanner);
        assertThat(register).isEqualTo(136904920099226L);
    }
}
