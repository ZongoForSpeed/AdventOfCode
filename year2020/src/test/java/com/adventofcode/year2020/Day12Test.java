package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test extends AbstractTest {
    Day12Test() {
        super(2020, 12);
    }

    @Test
    void rainRisk1() {
        var actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        var manhattanDistance = Day12.moveWithDirectionDistance(actions);
        assertThat(manhattanDistance).isEqualTo(25);
    }

    @Test
    void rainRisk2() {
        var actions = List.of("F10",
                "N3",
                "F7",
                "R90",
                "F11");

        var manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(286);
    }

    @Override
    public void partOne(Scanner scanner) {
        var actions = FileUtils.readLines(scanner);

        assertThat(Day12.moveWithDirectionDistance(actions)).isEqualTo(590);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var actions = FileUtils.readLines(scanner);

        var manhattanDistance = Day12.moveWithWaypointDistance(actions);
        assertThat(manhattanDistance).isEqualTo(42013);
    }
}
