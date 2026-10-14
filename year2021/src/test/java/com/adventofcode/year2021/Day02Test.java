package com.adventofcode.year2021;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AdventOfCode;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 2)
class Day02Test {
    @Test
    void inputExample() {
        var input = """
                forward 5
                down 5
                forward 8
                up 3
                down 8
                forward 2""";

        try (var scanner = new Scanner(input)) {
            IntegerPair p = Day02.divePartOne(scanner);

            assertThat(p.left()).isEqualTo(15);
            assertThat(p.right()).isEqualTo(10);
        }

        try (var scanner = new Scanner(input)) {
            var triple = Day02.divePartTwo(scanner);
            assertThat(triple).isEqualTo(Triple.of(15, 60, 10));
        }
    }

    @Test
    void partOne(Scanner scanner) {
        IntegerPair p = Day02.divePartOne(scanner);
        assertThat(p.left() * p.right()).isEqualTo(1561344);
    }

    @Test
    void partTwo(Scanner scanner) {
        var triple = Day02.divePartTwo(scanner);
        assertThat(triple).isEqualTo(Triple.of(2033, 909225, 768));
        assertThat(triple.getLeft() * triple.getMiddle()).isEqualTo(1848454425);
    }

}
