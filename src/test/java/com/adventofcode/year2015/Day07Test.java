package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    @Test
    void inputExample() {
        String input = """
                123 -> x
                456 -> y
                x AND y -> d
                x OR y -> e
                x LSHIFT 2 -> f
                y RSHIFT 2 -> g
                NOT x -> h
                NOT y -> i""";

        Map<String, Integer> map = Day07.runAssemblyPartOne(new Scanner(input));

        LOGGER.info("Memory: {}", map);
        assertThat(map).containsAllEntriesOf(
                Map.of(
                        "d", 72,
                        "e", 507,
                        "f", 492,
                        "g", 114,
                        "h", 65412,
                        "i", 65079,
                        "x", 123,
                        "y", 456
                )
        );

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2015/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.runAssemblyPartOne(scanner)).containsEntry("a", 46065);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2015/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.runAssemblyPartTwo(scanner)).containsEntry("a", 14134);
        }
    }

}
