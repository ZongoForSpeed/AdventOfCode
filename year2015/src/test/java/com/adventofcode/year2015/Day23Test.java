package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day23Test extends AbstractTest {
    Day23Test() {
        super(2015, 23);
    }

    @Test
    void inputExample() {
        String input = """
                inc a
                jio a, +2
                tpl a
                inc a""";

        Scanner scanner = new Scanner(input);
        Map<Character, Long> registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('a', 2L);

    }

    @Override
    public void partOne(Scanner scanner) {
        Map<Character, Long> registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('b', 255L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Map<Character, Long> registers = Day23.runInstructions(scanner, 1);
        assertThat(registers).containsEntry('b', 334L);
    }
}
