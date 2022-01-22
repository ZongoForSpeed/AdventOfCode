package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day14Test {

    @Test
    void inputExample() {
        String input = """
                Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.""";

        assertThat(Day14.raceReindeerPartOne(new Scanner(input), 1000)).isEqualTo(1120);
        assertThat(Day14.raceReindeerPartTwo(new Scanner(input), 1000)).containsAllEntriesOf(Map.of("Comet", 312, "Dancer", 689));
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2015/day/14/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day14.raceReindeerPartOne(scanner, 2503)).isEqualTo(2696);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/2015/day/14/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day14.raceReindeerPartTwo(scanner, 2503)).containsEntry("Rudolph", 1084);
        }
    }

}
