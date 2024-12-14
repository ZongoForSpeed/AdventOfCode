package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test extends AbstractTest {
    Day03Test() {
        super(2016, 3);
    }

    @Test
    void inputExample() {
        String input = """
                101 301 501
                102 302 502
                103 303 503
                201 401 601
                202 402 602
                203 403 603""";

        int count = Day03.countTrianglesPartTwo(new Scanner(input));
        assertThat(count).isEqualTo(6);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day03.countTrianglesPartOne(scanner)).isEqualTo(1032);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day03.countTrianglesPartTwo(scanner)).isEqualTo(1838);
    }

}
