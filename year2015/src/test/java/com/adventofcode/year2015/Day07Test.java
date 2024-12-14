package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test extends AbstractTest {
    Day07Test() {
        super(2015, 7);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day07.runAssemblyPartOne(scanner)).containsEntry("a", 46065);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day07.runAssemblyPartTwo(scanner)).containsEntry("a", 14134);
    }

}
