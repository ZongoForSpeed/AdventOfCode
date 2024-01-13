package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    @Test
    void inputExample() {
        String input = """
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581""";

        assertThat(Day15.chiton(new Scanner(input))).isEqualTo(40);
        assertThat(Day15.chiton(new Scanner(input), Day15::repeat)).isEqualTo(315);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2021/day/15/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day15.chiton(scanner)).isEqualTo(537);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2021/day/15/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day15.chiton(scanner, Day15::repeat)).isEqualTo(2881);
        }
    }

}
