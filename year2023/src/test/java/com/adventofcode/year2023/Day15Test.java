package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 15)
class Day15Test {

    @Test
    void inputExample() {
        Assertions.assertThat(Day15.PartOne.hash("HASH")).isEqualTo(52);

        var input = """
                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""";

        {
            var scanner = new Scanner(input);
            long sum = Day15.PartOne.sumHash(scanner);
            Assertions.assertThat(sum).isEqualTo(1320);
        }

        {
            var scanner = new Scanner(input);
            long focusingPower = Day15.PartTwo.focusingPower(scanner);
            Assertions.assertThat(focusingPower).isEqualTo(145);
        }
    }

    @Test
    void partOne(Scanner scanner) {

        long sum = Day15.PartOne.sumHash(scanner);
        Assertions.assertThat(sum).isEqualTo(516469);
    }

    @Test
    void partTwo(Scanner scanner) {
        long focusingPower = Day15.PartTwo.focusingPower(scanner);
        Assertions.assertThat(focusingPower).isEqualTo(221627);
    }

}
