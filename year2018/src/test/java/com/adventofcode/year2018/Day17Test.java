package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2018, day = 17)
class Day17Test {
    @Test
    void example() {
        var input = """
                x=495, y=2..7
                y=7, x=495..501
                x=501, y=3..7
                x=498, y=2..4
                x=506, y=1..2
                x=498, y=10..13
                x=504, y=10..13
                y=13, x=498..504""";
        var day17 = new Day17(new Scanner(input));
        assertThat(day17.partOne()).isEqualTo(57);
        assertThat(day17.partTwo()).isEqualTo(29);
    }

    @Test
    void partOne(Scanner scanner) {
        var day17 = new Day17(scanner);
        assertThat(day17.partOne()).isEqualTo(31861);
    }

    @Test
    void partTwo(Scanner scanner) {
        var day17 = new Day17(scanner);
        assertThat(day17.partTwo()).isEqualTo(26030);
    }

}
