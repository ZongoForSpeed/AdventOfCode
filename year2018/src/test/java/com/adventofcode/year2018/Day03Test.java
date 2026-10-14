package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 3)
class Day03Test {
    @Test
    void inputExample() {
        var input = """
                #1 @ 1,3: 4x4
                #2 @ 3,1: 4x4
                #3 @ 5,5: 2x2""";

        assertThat(Day03.overlapArea(new Scanner(input))).isEqualTo(4);
        assertThat(Day03.findClaimId(new Scanner(input))).isEqualTo(3);
    }


    @Test
    void partOne(Scanner scanner) {
        assertThat(Day03.overlapArea(scanner)).isEqualTo(110383);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day03.findClaimId(scanner)).isEqualTo(129);
    }

}
