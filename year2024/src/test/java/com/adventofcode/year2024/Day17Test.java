package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test extends AbstractTest {

    Day17Test() {
        super(2024, 17);
    }

    @Test
    void testExample1() {
        String input = """
                Register A: 729
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0""";

        try (Scanner scanner = new Scanner(input)) {
            String join = Day17.partOne(scanner);
            assertThat(join).isEqualTo("4,6,3,5,6,3,5,2,1,0");
        }
    }

    @Test
    void testExample2() {
        String input = """
                Register A: 2024
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0""";

        try (Scanner scanner = new Scanner(input)) {
            String join = Day17.partOne(scanner);
            assertThat(join).isEqualTo("4,2,5,6,7,7,7,7,3,1,0");
        }
    }

    @Test
    void testExample3() {
        String input = """
                Register A: 2024
                Register B: 0
                Register C: 0
                
                Program: 0,3,5,4,3,0""";

        try (Scanner scanner = new Scanner(input)) {
            long register = Day17.partTwoBruteForce(scanner);
            assertThat(register).isEqualTo(117440);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        String join = Day17.partOne(scanner);
        assertThat(join).isEqualTo("3,6,3,7,0,7,0,3,0");
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        long register = Day17.partTwo(scanner);
        assertThat(register).isEqualTo(136904920099226L);
    }
}
