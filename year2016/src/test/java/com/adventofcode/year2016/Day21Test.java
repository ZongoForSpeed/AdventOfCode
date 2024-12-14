package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test extends AbstractTest {
    Day21Test() {
        super(2016, 21);
    }

    @Test
    void inputExample() {
        String input = """
                swap position 4 with position 0
                swap letter d with letter b
                reverse positions 0 through 4
                rotate left 1 steps
                move position 1 to position 4
                move position 3 to position 0
                rotate based on position of letter b
                rotate based on position of letter d""";

        assertThat(Day21.scramblingFunction(new Scanner(input), "abcde")).isEqualTo("decab");
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day21.scramblingFunction(scanner, "abcdefgh")).isEqualTo("gcedfahb");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day21.unscramblingFunction(scanner, "fbgdceah")).isEqualTo("hegbdcfa");
    }
}
