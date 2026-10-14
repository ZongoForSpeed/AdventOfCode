package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 16)
class Day16Test {
    @Test
    void inputExample() {
        var input = "s1,x3/4,pe/b";

        assertThat(Day16.danceMoves(input, 5, 1)).isEqualTo("baedc");
        assertThat(Day16.danceMoves(input, 5, 2)).isEqualTo("ceadb");
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1)).isEqualTo("dcmlhejnifpokgba");
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day16.danceMoves(scanner.nextLine(), 16, 1_000_000_000)).isEqualTo("ifocbejpdnklamhg");
    }

}
