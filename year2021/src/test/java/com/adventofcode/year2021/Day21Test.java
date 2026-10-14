package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 21)
class Day21Test {
    @Test
    void inputExample() {
        var input = """
                Player 1 starting position: 4
                Player 2 starting position: 8""";

        assertThat(Day21.playPartOne(new Scanner(input))).isEqualTo(739785);
        assertThat(Day21.playPartTwo(new Scanner(input))).isEqualTo(444356092776315L);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day21.playPartOne(scanner)).isEqualTo(503478);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day21.playPartTwo(scanner)).isEqualTo(716241959649754L);
    }

}
