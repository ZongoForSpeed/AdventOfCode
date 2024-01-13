package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    @Test
    void testRainRisk1() {
        List<String> actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        int manhattanDistance = Day12.moveWithDirectionDistance(actions);
        assertThat(manhattanDistance).isEqualTo(25);
    }

    @Test
    void testRainRisk2() {
        List<String> actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        int manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(286);
    }

    @Test
    void inputRainRisk1() throws IOException {
        List<String> actions = FileUtils.readLines("/2020/day/12/input");

        assertThat(Day12.moveWithDirectionDistance(actions)).isEqualTo(590);
    }

    @Test
    void inputRainRisk2() throws IOException {
        List<String> actions = FileUtils.readLines("/2020/day/12/input");

        int manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(42013);
    }

}
