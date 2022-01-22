package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/11/input");
        Day11.HullPaintingRobot robot = Day11.hullPaintingRobotPartOne(line);
        assertThat(robot.getHull()).hasSize(1732);
        robot.getHull().print(v -> v == 1 ? 'X' : ' ');
    }


    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/11/input");
        Day11.HullPaintingRobot robot = Day11.hullPaintingRobotPartTwo(line);
        assertThat(robot.getHull()).hasSize(249);
        List<String> hull = robot.hull.print(v -> v == 1 ? 'X' : ' ');
        assertThat(hull).containsExactly(
                "  XX  XXX   XX  X    XXXX X  X X  X   XX   ",
                " X  X X  X X  X X    X    X  X X  X    X   ",
                " X  X XXX  X    X    XXX  X  X XXXX    X   ",
                " XXXX X  X X    X    X    X  X X  X    X   ",
                " X  X X  X X  X X    X    X  X X  X X  X   ",
                " X  X XXX   XX  XXXX X     XX  X  X  XX    "
        );
    }


}
