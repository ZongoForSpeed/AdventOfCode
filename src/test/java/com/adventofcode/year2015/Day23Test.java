package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day23Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day23Test.class.getResourceAsStream("/2015/day/23/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            Map<Character, Long> registers = Day23.runInstructions(scanner, 0);
            assertThat(registers).containsEntry('b', 255L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day23Test.class.getResourceAsStream("/2015/day/23/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            Map<Character, Long> registers = Day23.runInstructions(scanner, 1);
            assertThat(registers).containsEntry('b', 334L);
        }
    }
}
