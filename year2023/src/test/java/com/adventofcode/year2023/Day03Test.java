package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day03Test extends AbstractTest {

    Day03Test() {
        super(2023, 3);
    }

    @Test
    void inputExample() {
        String input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;

        Assertions.assertThat(Day03.PartOne.gearRatios(new Scanner(input)))
                .isEqualTo(4361);


        Assertions.assertThat(Day03.PartTwo.gearRatios(new Scanner(input)))
                .isEqualTo(467835);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2023/day/03/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2023/day/03/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        Assertions.assertThat(Day03.PartOne.gearRatios(scanner)).isEqualTo(540025);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Assertions.assertThat(Day03.PartTwo.gearRatios(scanner)).isEqualTo(84584891);
    }

}
