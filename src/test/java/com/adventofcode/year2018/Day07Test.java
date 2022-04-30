package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test {

    @Test
    void inputExample() {
        String input = """
                Step C must be finished before step A can begin.
                Step C must be finished before step F can begin.
                Step A must be finished before step B can begin.
                Step A must be finished before step D can begin.
                Step B must be finished before step E can begin.
                Step D must be finished before step E can begin.
                Step F must be finished before step E can begin.""";

        assertThat(Day07.getCorrectOrder(new Scanner(input))).isEqualTo("CABDFE");

        assertThat(Day07.getCompletionTime(new Scanner(input), 2, 0)).isEqualTo(15);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2018/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.getCorrectOrder(scanner)).isEqualTo("GNJOCHKSWTFMXLYDZABIREPVUQ");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2018/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.getCompletionTime(scanner, 5, 60)).isEqualTo(886);
        }
    }

}
