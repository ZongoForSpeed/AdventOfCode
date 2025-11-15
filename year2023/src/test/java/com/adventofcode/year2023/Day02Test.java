package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day02Test extends AbstractTest {

    protected Day02Test() {
        super(2023, 2);
    }

    @Test
    void examplePartOne() {
        String input = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""";

        Scanner scanner = new Scanner(input);

        Assertions.assertThat(Day02.PartOne.cubeConundrum(scanner)).isEqualTo(8);
    }


    @Test
    void examplePartTwo() {
        String input = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""";

        Scanner scanner = new Scanner(input);

        Assertions.assertThat(Day02.PartTwo.cubeConundrum(scanner)).isEqualTo(2286);
    }

    @Override
    public void partOne(Scanner scanner) {
        Assertions.assertThat(Day02.PartOne.cubeConundrum(scanner)).isEqualTo(2683);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Assertions.assertThat(Day02.PartTwo.cubeConundrum(scanner)).isEqualTo(49710);
    }

}
