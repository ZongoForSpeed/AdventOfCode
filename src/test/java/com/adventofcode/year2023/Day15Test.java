package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day15Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day15Test.class.getResourceAsStream("/2023/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {

            long sum = Day15.PartOne.sumHash(scanner);
            Assertions.assertThat(sum).isEqualTo(516469);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day15Test.class.getResourceAsStream("/2023/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {

            long focusingPower = Day15.PartTwo.focusingPower(scanner);
            Assertions.assertThat(focusingPower).isEqualTo(221627);
        }
    }

}
