package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test extends AbstractTest {
    Day03Test() {
        super(2018, 3);
    }

    @Test
    void inputExample() {
        String input = """
                #1 @ 1,3: 4x4
                #2 @ 3,1: 4x4
                #3 @ 5,5: 2x2""";

        assertThat(Day03.overlapArea(new Scanner(input))).isEqualTo(4);
        assertThat(Day03.findClaimId(new Scanner(input))).isEqualTo(3);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day03.overlapArea(scanner)).isEqualTo(110383);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day03.findClaimId(scanner)).isEqualTo(129);
    }

}
