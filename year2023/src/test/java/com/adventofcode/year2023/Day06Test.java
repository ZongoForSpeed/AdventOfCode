package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 6)
class Day06Test {

    @Test
    void examplePartOne() {
        var input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        var scanner = new Scanner(input);

        long reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(288);
    }

    @Test
    void examplePartTwo() {
        var input = """
                Time:      71530
                Distance:  940200""";

        var scanner = new Scanner(input);

        long reduce = Day06.PartTwo.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(71503);
    }

    @Test
    void partOne(Scanner scanner) {
        long reduce = Day06.PartOne.raceRecord(scanner);
        Assertions.assertThat(reduce).isEqualTo(131376);
    }

    @Test
    void partTwo() {
        var input = """
                Time:       51699878
                Distance:   377117112241505
                """;

        try (var scanner = new Scanner(input)) {
            long reduce = Day06.PartTwo.raceRecord(scanner);
            Assertions.assertThat(reduce).isEqualTo(34123437L);
        }
    }

}
