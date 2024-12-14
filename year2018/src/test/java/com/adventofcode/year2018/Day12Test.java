package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test extends AbstractTest {
    Day12Test() {
        super(2018, 12);
    }

    @Test
    void inputExample() {
        String input = """
                initial state: #..#.#..##......###...###
                
                ...## => #
                ..#.. => #
                .#... => #
                .#.#. => #
                .#.## => #
                .##.. => #
                .#### => #
                #.#.# => #
                #.### => #
                ##.#. => #
                ##.## => #
                ###.. => #
                ###.# => #
                ####. => #""";
        Scanner scanner = new Scanner(input);

        assertThat(Day12.getPlants(scanner, 20)).isEqualTo(325);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day12.getPlants(scanner, 20)).isEqualTo(2571);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day12.getPlants(scanner, 50000000000L)).isEqualTo(3100000000655L);
    }
}
