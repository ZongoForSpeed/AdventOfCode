package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test extends AbstractTest {

    protected Day10Test() {
        super(2025, 10);
    }

    @Test
    void inputExample() {
        String input = """
                [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""";


        try (Scanner scanner = new Scanner(input)) {
            long sum = Day10.factoryPartOne(scanner);
            assertThat(sum).isEqualTo(7);
        }

        try (Scanner scanner = new Scanner(input)) {
            long sum = Day10.factoryPartTwo(scanner);
            assertThat(sum).isEqualTo(33);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        long sum = Day10.factoryPartOne(scanner);
        assertThat(sum).isEqualTo(486);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        long sum = Day10.factoryPartTwo(scanner);
        assertThat(sum).isEqualTo(17820);
    }
}
