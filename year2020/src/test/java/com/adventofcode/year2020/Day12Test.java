package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 12)
class Day12Test {
    @Test
    void rainRisk1() {
        var actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        int manhattanDistance = Day12.moveWithDirectionDistance(actions);
        assertThat(manhattanDistance).isEqualTo(25);
    }

    @Test
    void rainRisk2() {
        var actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        int manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(286);
    }

    @Test
    void partOne(Scanner scanner) {
        var actions = FileUtils.readLines(scanner);

        assertThat(Day12.moveWithDirectionDistance(actions)).isEqualTo(590);
    }

    @Test
    void partTwo(Scanner scanner) {
        var actions = FileUtils.readLines(scanner);

        int manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(42013);
    }
}
