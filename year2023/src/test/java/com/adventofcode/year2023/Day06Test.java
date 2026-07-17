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
        var input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        var scanner = new Scanner(input);

        var reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(288);
    }

    @Test
    void examplePartTwo() {
        var input = """
                Time:      71530
                Distance:  940200""";

        var scanner = new Scanner(input);

        var reduce = Day06.PartTwo.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(71503);
    }

    @Override
    public void partOne(Scanner scanner) {
        var reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(131376);
    }

    @Override
    public void partTwo(Scanner ignored) {
        var input = """
                Time:       51699878
                Distance:   377117112241505
                """;

        try (var scanner = new Scanner(input)) {
            var reduce = Day06.PartTwo.raceRecord(scanner);
            Assertions.assertThat(reduce).isEqualTo(34123437L);
        }
    }

}
