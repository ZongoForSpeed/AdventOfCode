package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 12)
class Day12Test {
    @Test
    void inputExample() {
        var input = """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1""";

        {
            var scanner = new Scanner(input);
            int count = Day12.PartOne.countArrangements(scanner);
//
            Assertions.assertThat(count).isEqualTo(21);
        }

        {
            var scanner = new Scanner(input);
            long count = Day12.PartTwo.countArrangements(scanner, 1);
            Assertions.assertThat(count).isEqualTo(21);
        }

        {
            var scanner = new Scanner(input);
            long count = Day12.PartTwo.countArrangements(scanner, 5);
            Assertions.assertThat(count).isEqualTo(525152);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int count = Day12.PartOne.countArrangements(scanner);
        Assertions.assertThat(count).isEqualTo(6871);
    }

    @Test
    void partTwo(Scanner scanner) {
        long count = Day12.PartTwo.countArrangements(scanner, 5);
        Assertions.assertThat(count).isEqualTo(2043098029844L);
    }

}
