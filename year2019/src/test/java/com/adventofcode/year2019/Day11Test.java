package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test extends AbstractTest {
    Day11Test() {
        super(2019, 11);
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        Day11.HullPaintingRobot robot = Day11.hullPaintingRobotPartOne(line);
        assertThat(robot.getHull()).hasSize(1732);
        robot.getHull().print(v -> v == 1 ? 'X' : ' ');
    }

    @Override
    public void partTwo(Scanner scanner) {

        String line = scanner.nextLine();
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
