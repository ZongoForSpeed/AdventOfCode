package com.adventofcode.year2017;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 21)
class Day21Test {
    @Test
    void inputExample() {
        var input = """
                ../.# => ##./#../...
                .#./..#/### => #..#/..../..../#..#""";

        var scanner = new Scanner(input);

        assertThat(Day21.buildFractal(scanner, 2)).extracting(BooleanMap::cardinality).isEqualTo(12L);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day21.buildFractal(scanner, 5)).extracting(BooleanMap::cardinality).isEqualTo(152L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day21.buildFractal(scanner, 18)).extracting(BooleanMap::cardinality).isEqualTo(1956174L);
    }

}
