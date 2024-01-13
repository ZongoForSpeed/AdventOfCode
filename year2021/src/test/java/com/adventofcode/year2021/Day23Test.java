package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day23Test.class.getResourceAsStream("/2021/day/23/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day23.computeCostPartOne(scanner)).isEqualTo(15111);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day23Test.class.getResourceAsStream("/2021/day/23/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day23.computeCostPartTwo(scanner)).isEqualTo(47625);
        }
    }

}
