package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;





import java.util.Scanner;

class Day04Test extends AbstractTest {
    Day04Test() {
        super(2022, 4);
    }

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
            var count = Day04.PartOne.countFullyContained(scanner);
            Assertions.assertThat(count).isEqualTo(2);
        }

        {
            var scanner = new Scanner(input);
            var count = Day04.PartTwo.countOverlaps(scanner);
            Assertions.assertThat(count).isEqualTo(4);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var count = Day04.PartOne.countFullyContained(scanner);
        Assertions.assertThat(count).isEqualTo(444);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var count = Day04.PartTwo.countOverlaps(scanner);
        Assertions.assertThat(count).isEqualTo(801);
    }

}
