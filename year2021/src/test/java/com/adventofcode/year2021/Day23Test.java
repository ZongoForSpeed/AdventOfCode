package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 23)
class Day23Test {
    @Test
    void inputExample() {
        var input = """
                #############
                #...........#
                ###B#C#B#D###
                  #A#D#C#A#
                  #########""";

        assertThat(Day23.computeCostPartOne(new Scanner(input))).isEqualTo(12521);
        assertThat(Day23.computeCostPartTwo(new Scanner(input))).isEqualTo(44169);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day23.computeCostPartOne(scanner)).isEqualTo(15111);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day23.computeCostPartTwo(scanner)).isEqualTo(47625);
    }

}
