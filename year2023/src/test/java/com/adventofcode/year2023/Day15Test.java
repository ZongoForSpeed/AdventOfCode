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

        String input = """
                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""";

        {
            Scanner scanner = new Scanner(input);
            long sum = Day15.PartOne.sumHash(scanner);
            Assertions.assertThat(sum).isEqualTo(1320);
        }

        {
            Scanner scanner = new Scanner(input);
            long focusingPower = Day15.PartTwo.focusingPower(scanner);
            Assertions.assertThat(focusingPower).isEqualTo(145);
        }
    }

    @Override
    public void partOne(Scanner scanner) {

        long sum = Day15.PartOne.sumHash(scanner);
        Assertions.assertThat(sum).isEqualTo(516469);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long focusingPower = Day15.PartTwo.focusingPower(scanner);
        Assertions.assertThat(focusingPower).isEqualTo(221627);
    }

}
