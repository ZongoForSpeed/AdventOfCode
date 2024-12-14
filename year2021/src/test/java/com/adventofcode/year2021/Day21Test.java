package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2021, 21);
    }

    @Test
    void inputExample() {
        String input = """
                Player 1 starting position: 4
                Player 2 starting position: 8""";

        assertThat(Day21.playPartOne(new Scanner(input))).isEqualTo(739785);
        assertThat(Day21.playPartTwo(new Scanner(input))).isEqualTo(444356092776315L);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day21.playPartOne(scanner)).isEqualTo(503478);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day21.playPartTwo(scanner)).isEqualTo(716241959649754L);
    }

}
