package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test extends AbstractTest {
    Day05Test() {
        super(2018, 5);
    }

    @Test
    void inputExample() {
        String input = "dabAcCaCBAcCcaDA";

        assertThat(Day05.getReductionSize(input, _ -> true)).isEqualTo(10);

        int minSize = Day05.minReductionSize(input);

        assertThat(minSize).isEqualTo(4);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day05.getReductionSize(scanner.nextLine(), _ -> true)).isEqualTo(11310);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day05.minReductionSize(scanner.nextLine())).isEqualTo(6020);
    }

}
