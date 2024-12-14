package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test extends AbstractTest {

    protected Day13Test() {
        super(2024, 13);
    }

    @Test
    void inputExample() {
        String input = """
                Button A: X+94, Y+34
                Button B: X+22, Y+67
                Prize: X=8400, Y=5400
                
                Button A: X+26, Y+66
                Button B: X+67, Y+21
                Prize: X=12748, Y=12176
                
                Button A: X+17, Y+86
                Button B: X+84, Y+37
                Prize: X=7870, Y=6450
                
                Button A: X+69, Y+23
                Button B: X+27, Y+71
                Prize: X=18641, Y=10279""";

        try (Scanner scanner = new Scanner(input)) {
            long cost = Day13.partOne(scanner);
            assertThat(cost).isEqualTo(480);
        }

        try (Scanner scanner = new Scanner(input)) {
            long cost = Day13.partTwo(scanner);
            assertThat(cost).isEqualTo(875318608908L);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day13.partOne(scanner)).isEqualTo(33427L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day13.partTwo(scanner)).isEqualTo(91649162972270L);
    }

}
