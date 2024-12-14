package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test extends AbstractTest {
    Day03Test() {
        super(2020, 3);
    }

    @Test
    void testTobogganTrajectory() {
        List<String> map = List.of("..##.......",
                "#...#...#..",
                ".#....#..#.",
                "..#.#...#.#",
                ".#...##..#.",
                "..#.##.....",
                ".#.#.#....#",
                ".#........#",
                "#.##...#...",
                "#...##....#",
                ".#..#...#.#");

        assertThat(Day03.tobogganTrajectory(map, 1, 1)).isEqualTo(2);
        assertThat(Day03.tobogganTrajectory(map, 3, 1)).isEqualTo(7);
        assertThat(Day03.tobogganTrajectory(map, 5, 1)).isEqualTo(3);
        assertThat(Day03.tobogganTrajectory(map, 7, 1)).isEqualTo(4);
        assertThat(Day03.tobogganTrajectory(map, 1, 2)).isEqualTo(2);

        assertThat(Day03.checkAllTobogganTrajectories(map)).isEqualTo(336);

    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(Day03.tobogganTrajectory(lines, 3, 1)).isEqualTo(234);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(Day03.checkAllTobogganTrajectories(lines)).isEqualTo(5813773056L);
    }
}
