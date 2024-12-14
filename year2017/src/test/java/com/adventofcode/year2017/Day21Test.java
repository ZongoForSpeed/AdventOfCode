package com.adventofcode.year2017;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test extends AbstractTest {
    Day21Test() {
        super(2017, 21);
    }

    @Test
    void inputExample() {
        String input = """
                ../.# => ##./#../...
                .#./..#/### => #..#/..../..../#..#""";

        Scanner scanner = new Scanner(input);

        assertThat(Day21.buildFractal(scanner, 2)).extracting(BooleanMap::cardinality).isEqualTo(12L);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day21.buildFractal(scanner, 5)).extracting(BooleanMap::cardinality).isEqualTo(152L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day21.buildFractal(scanner, 18)).extracting(BooleanMap::cardinality).isEqualTo(1956174L);
    }

}
