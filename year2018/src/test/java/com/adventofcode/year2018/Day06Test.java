package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test extends AbstractTest {
    Day06Test() {
        super(2018, 6);
    }

    @Test
    void inputExample() {
        String input = """
                1, 1
                1, 6
                8, 3
                3, 4
                5, 5
                8, 9""";

        assertThat(Day06.maxArea(new Scanner(input))).isEqualTo(17);
        assertThat(Day06.region(new Scanner(input), 30)).isEqualTo(16);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.maxArea(scanner)).isEqualTo(4060);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.region(scanner, 10000)).isEqualTo(36136);
    }

}
