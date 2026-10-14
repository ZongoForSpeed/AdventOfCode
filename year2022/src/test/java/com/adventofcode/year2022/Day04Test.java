package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;





import java.util.Scanner;

@AdventOfCode(year = 2022, day = 4)
class Day04Test {
    @Test
    void inputExample() {
        var input = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8""";

        {
            var scanner = new Scanner(input);
            long count = Day04.PartOne.countFullyContained(scanner);
            Assertions.assertThat(count).isEqualTo(2);
        }

        {
            var scanner = new Scanner(input);
            long count = Day04.PartTwo.countOverlaps(scanner);
            Assertions.assertThat(count).isEqualTo(4);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long count = Day04.PartOne.countFullyContained(scanner);
        Assertions.assertThat(count).isEqualTo(444);
    }

    @Test
    void partTwo(Scanner scanner) {
        long count = Day04.PartTwo.countOverlaps(scanner);
        Assertions.assertThat(count).isEqualTo(801);
    }

}
