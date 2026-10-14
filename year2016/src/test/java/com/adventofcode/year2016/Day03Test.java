package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 3)
class Day03Test {
    @Test
    void inputExample() {
        var input = """
                101 301 501
                102 302 502
                103 303 503
                201 401 601
                202 402 602
                203 403 603""";

        int count = Day03.countTrianglesPartTwo(new Scanner(input));
        assertThat(count).isEqualTo(6);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day03.countTrianglesPartOne(scanner)).isEqualTo(1032);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day03.countTrianglesPartTwo(scanner)).isEqualTo(1838);
    }

}
