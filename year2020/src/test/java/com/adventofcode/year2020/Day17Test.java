package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 17)
class Day17Test {
    @Test
    void conwayCubes() {
        var initialState = List.of(".#.",
                "..#",
                "###");

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(112);
        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(848);
    }

    @Test
    void partOne(Scanner scanner) {
        var initialState = FileUtils.readLines(scanner);

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(215);
    }

    @Test
    void partTwo(Scanner scanner) {
        var initialState = FileUtils.readLines(scanner);

        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(1728);
    }
}
