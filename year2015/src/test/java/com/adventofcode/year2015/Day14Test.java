package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day14Test extends AbstractTest {
    Day14Test() {
        super(2015, 14);
    }

    @Test
    void inputExample() {
        String input = """
                Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.""";

        assertThat(Day14.raceReindeerPartOne(new Scanner(input), 1000)).isEqualTo(1120);
        assertThat(Day14.raceReindeerPartTwo(new Scanner(input), 1000)).containsAllEntriesOf(Map.of("Comet", 312, "Dancer", 689));
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day14.raceReindeerPartOne(scanner, 2503)).isEqualTo(2696);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day14.raceReindeerPartTwo(scanner, 2503)).containsEntry("Rudolph", 1084);
    }

}
