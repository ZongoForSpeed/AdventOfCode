package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2018, day = 12)
class Day12Test {
    @Test
    void inputExample() {
        var input = """
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
        var scanner = new Scanner(input);

        assertThat(Day12.getPlants(scanner, 20)).isEqualTo(325);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day12.getPlants(scanner, 20)).isEqualTo(2571);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day12.getPlants(scanner, 50000000000L)).isEqualTo(3100000000655L);
    }
}
