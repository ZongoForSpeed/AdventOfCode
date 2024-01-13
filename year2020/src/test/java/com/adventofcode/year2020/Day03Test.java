package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

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

    @Test
    void inputTobogganTrajectory() throws IOException {
        List<String> lines = FileUtils.readLines("/2020/day/3/input");

        assertThat(Day03.tobogganTrajectory(lines, 3, 1)).isEqualTo(234);

        assertThat(Day03.checkAllTobogganTrajectories(lines)).isEqualTo(5813773056L);
    }
}
