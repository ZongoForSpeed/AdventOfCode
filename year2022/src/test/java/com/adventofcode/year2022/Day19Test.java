package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test extends AbstractTest {
    Day19Test() {
        super(2022, 19);
    }

    @Test
    void inputExample() {
        String input = """
                Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.""";

        {
            Scanner scanner = new Scanner(input);
            long qualityLevel = Day19.computeQualityLevelPartOne(scanner);
            assertThat(qualityLevel).isEqualTo(33);
        }

        {
            Scanner scanner = new Scanner(input);
            long qualityLevel = Day19.computeQualityLevelPartTwo(scanner);
            assertThat(qualityLevel).isEqualTo(3472);
        }
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day19.computeQualityLevelPartOne(scanner)).isEqualTo(851);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day19.computeQualityLevelPartTwo(scanner)).isEqualTo(12160);
    }
}
