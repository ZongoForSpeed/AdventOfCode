package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test extends AbstractTest {
    Day23Test() {
        super(2021, 23);
    }

    @Test
    void inputExample() {
        String input = """
                #############
                #...........#
                ###B#C#B#D###
                  #A#D#C#A#
                  #########""";

        assertThat(Day23.computeCostPartOne(new Scanner(input))).isEqualTo(12521);
        assertThat(Day23.computeCostPartTwo(new Scanner(input))).isEqualTo(44169);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day23.computeCostPartOne(scanner)).isEqualTo(15111);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day23.computeCostPartTwo(scanner)).isEqualTo(47625);
    }

}
