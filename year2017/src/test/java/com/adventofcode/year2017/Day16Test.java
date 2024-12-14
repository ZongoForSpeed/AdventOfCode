package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test extends AbstractTest {
    Day16Test() {
        super(2017, 16);
    }

    @Test
    void inputExample() {
        String input = "s1,x3/4,pe/b";

        assertThat(Day16.danceMoves(input, 5, 1)).isEqualTo("baedc");
        assertThat(Day16.danceMoves(input, 5, 2)).isEqualTo("ceadb");
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1)).isEqualTo("dcmlhejnifpokgba");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1_000_000_000)).isEqualTo("ifocbejpdnklamhg");
    }

}
