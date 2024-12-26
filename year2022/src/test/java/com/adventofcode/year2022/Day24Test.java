package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test extends AbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    Day24Test() {
        super(2022, 24);
    }

    @Test
    void inputExample1() {
        String input = """
                #.#####
                #.....#
                #>....#
                #.....#
                #...v.#
                #.....#
                #####.#""";

        try (Scanner scanner = new Scanner(input)) {
            Day24.Basin basin = Day24.readInput(scanner);

            LOGGER.info("blizzard {}: {}", 1, basin.blizzard(1));
            LOGGER.info("blizzard {}: {}", 2, basin.blizzard(2));
            LOGGER.info("blizzard {}: {}", 3, basin.blizzard(3));
        }
    }

    @Test
    void inputExample2() {
        String input = """
                #.#####
                #...v.#
                #..>..#
                #.....#
                #.....#
                #.....#
                #####.#""";

        try (Scanner scanner = new Scanner(input)) {
            Day24.Basin basin = Day24.readInput(scanner);

            LOGGER.info("blizzard {}: {}", 1, basin.blizzard(1));
            LOGGER.info("blizzard {}: {}", 2, basin.blizzard(2));
            LOGGER.info("blizzard {}: {}", 3, basin.blizzard(3));
        }
    }

    @Test
    void inputExample3() {
        String input = """
                #.######
                #>>.<^<#
                #.<..<<#
                #>v.><>#
                #<^v^^>#
                ######.#""";

        try (Scanner scanner = new Scanner(input)) {
            long path = Day24.partOne(scanner);
            assertThat(path).isEqualTo(18);
        }

        try (Scanner scanner = new Scanner(input)) {
            long path = Day24.partTwo(scanner);
            assertThat(path).isEqualTo(54);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        long path = Day24.partOne(scanner);
        assertThat(path).isEqualTo(288);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        long path = Day24.partTwo(scanner);
        assertThat(path).isEqualTo(861);
    }

}
