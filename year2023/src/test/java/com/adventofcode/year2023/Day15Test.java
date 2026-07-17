package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day15Test extends AbstractTest {

    Day15Test() {
        super(2023, 15);
    }

    @Test
    void inputExample() {
        Assertions.assertThat(Day15.PartOne.hash("HASH")).isEqualTo(52);

        var input = """
                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""";

        {
            var scanner = new Scanner(input);
            var sum = Day15.PartOne.sumHash(scanner);
            Assertions.assertThat(sum).isEqualTo(1320);
        }

        {
            var scanner = new Scanner(input);
            var focusingPower = Day15.PartTwo.focusingPower(scanner);
            Assertions.assertThat(focusingPower).isEqualTo(145);
        }
    }

    @Override
    public void partOne(Scanner scanner) {

        var sum = Day15.PartOne.sumHash(scanner);
        Assertions.assertThat(sum).isEqualTo(516469);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var focusingPower = Day15.PartTwo.focusingPower(scanner);
        Assertions.assertThat(focusingPower).isEqualTo(221627);
    }

}
