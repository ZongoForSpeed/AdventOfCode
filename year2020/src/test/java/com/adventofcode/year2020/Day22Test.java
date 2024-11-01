package com.adventofcode.year2020;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {

    @Test
    void testCrabCombat() {
        String input = """
                Player 1:
                9
                2
                6
                3
                1
                                
                Player 2:
                5
                8
                4
                7
                10""";

        Scanner scanner = new Scanner(input);
        long point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(306);

        long recursiveCombat = Day22.playRecursiveCombat(new Scanner(input));
        assertThat(recursiveCombat).isEqualTo(291);
    }

    @Test
    void inputCrabCombatPartOne() throws IOException {
        try (InputStream inputStream = Day22Test.class.getResourceAsStream("/2020/day/22/input"); Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long point = Day22.playCrabCombat(scanner);
            assertThat(point).isEqualTo(33393);
        }
    }

    @Test
    void inputCrabCombatPartTwo() throws IOException {
        try (InputStream inputStream = Day22Test.class.getResourceAsStream("/2020/day/22/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long recursiveCombat = Day22.playRecursiveCombat(scanner);
            assertThat(recursiveCombat).isEqualTo(31963);
        }
    }
}
