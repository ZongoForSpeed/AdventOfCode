package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 23)
class Day23Test {
    @Test
    void inputExample() {
        var input = """
                inc a
                jio a, +2
                tpl a
                inc a""";

        var scanner = new Scanner(input);
        var registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('a', 2L);

    }

    @Test
    void partOne(Scanner scanner) {
        var registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('b', 255L);
    }

    @Test
    void partTwo(Scanner scanner) {
        var registers = Day23.runInstructions(scanner, 1);
        assertThat(registers).containsEntry('b', 334L);
    }
}
