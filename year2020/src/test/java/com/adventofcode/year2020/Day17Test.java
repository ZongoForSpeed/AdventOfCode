package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void testConwayCubes() {
        List<String> initialState = List.of(".#.",
                "..#",
                "###");

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(112);
        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(848);
    }

    @Test
    void inputConwayCubes() throws IOException {
        List<String> initialState = FileUtils.readLines("/2020/day/17/input");

        assertThat(Day17.runConwayCubes(initialState, 6)).isEqualTo(215);
        assertThat(Day17.runConwayHyperCubes(initialState, 6)).isEqualTo(1728);
    }
}
