package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day06Test extends AbstractTest {
    Day06Test() {
        super(2023, 6);
    }

    @Test
    void examplePartOne() {
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        Scanner scanner = new Scanner(input);

        long reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(288);
    }

    @Test
    void examplePartTwo() {
        String input = """
                Time:      71530
                Distance:  940200""";

        Scanner scanner = new Scanner(input);

        long reduce = Day06.PartTwo.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(71503);
    }

    @Override
    public void partOne(Scanner scanner) {
        long reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(131376);
    }

    @Override
    public void partTwo(Scanner ignored) {
        String input = """
                Time:       51699878
                Distance:   377117112241505
                """;

        try (Scanner scanner = new Scanner(input)) {
            long reduce = Day06.PartTwo.raceRecord(scanner);
            Assertions.assertThat(reduce).isEqualTo(34123437L);
        }
    }

}
