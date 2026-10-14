package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 16)
class Day16Test {
    @Test
    void example() {
        var input = """
                Before: [3, 2, 1, 1]
                9 2 1 2
                After:  [3, 2, 2, 1]
                
                """;
        var scanner = new Scanner(input);
        assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(1);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(580);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day16.chronalClassificationPartTwo(scanner)).isEqualTo(537);
    }

}
