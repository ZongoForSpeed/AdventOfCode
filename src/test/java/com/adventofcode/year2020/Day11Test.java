package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @Test
    void testSeatingSystem() {
        List<String> inputGrid = List.of(
                "L.LL.LL.LL",
                "LLLLLLL.LL",
                "L.L.L..L..",
                "LLLL.LL.LL",
                "L.LL.LL.LL",
                "L.LLLLL.LL",
                "..L.L.....",
                "LLLLLLLLLL",
                "L.LLLLLL.L",
                "L.LLLLL.LL");

        char[][] grid = inputGrid.stream().map(String::toCharArray).toArray(char[][]::new);

        assertThat(Day11.simulationSeatingSystem(grid, false)).isEqualTo(37);
        assertThat(Day11.simulationSeatingSystem(grid, true)).isEqualTo(26);
    }

    @Test
    void testAdjacentOccupiedSeats1() {
        List<String> inputGrid = List.of(
                ".......#.",
                "...#.....",
                ".#.......",
                ".........",
                "..#L....#",
                "....#....",
                ".........",
                "#........",
                "...#.....");
        char[][] grid = inputGrid.stream().map(String::toCharArray).toArray(char[][]::new);
        int occupiedSeats = Day11.adjacentOccupiedSeats(grid, 4, 3, true);
        assertThat(occupiedSeats).isEqualTo(8);
    }

    @Test
    void testAdjacentOccupiedSeats2() {
        List<String> inputGrid = List.of(
                ".............",
                ".L.L.#.#.#.#.",
                ".............");
        char[][] grid = inputGrid.stream().map(String::toCharArray).toArray(char[][]::new);
        int occupiedSeats = Day11.adjacentOccupiedSeats(grid, 1, 1, true);
        assertThat(occupiedSeats).isZero();
    }

    @Test
    void testAdjacentOccupiedSeats3() {
        List<String> inputGrid1 = List.of(
                ".##.##.",
                "#.#.#.#",
                "##...##",
                "...L...",
                "##...##",
                "#.#.#.#",
                ".##.##.");

        char[][] grid1 = inputGrid1.stream().map(String::toCharArray).toArray(char[][]::new);
        int occupiedSeats = Day11.adjacentOccupiedSeats(grid1, 3, 3, true);
        assertThat(occupiedSeats).isZero();
    }

    @Test
    void inputSeatingSystem() throws IOException {
        List<String> inputGrid = FileUtils.readLines("/2020/day/11/input");

        char[][] grid = inputGrid.stream().map(String::toCharArray).toArray(char[][]::new);

        assertThat(Day11.simulationSeatingSystem(grid, false)).isEqualTo(2472);
        assertThat(Day11.simulationSeatingSystem(grid, true)).isEqualTo(2197);
    }
}
