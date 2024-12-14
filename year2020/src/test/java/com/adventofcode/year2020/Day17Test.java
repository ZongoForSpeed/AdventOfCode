package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test extends AbstractTest {
    Day17Test() {
        super(2020, 17);
    }

    @Test
    void testConwayCubes() {
        List<String> initialState = List.of(".#.",
                "..#",
                "###");

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(112);
        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(848);
    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> initialState = FileUtils.readLines(scanner);

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(215);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> initialState = FileUtils.readLines(scanner);

        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(1728);
    }
}
