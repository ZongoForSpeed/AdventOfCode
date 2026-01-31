package com.adventofcode.common.maths;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationsTest {
    @Test
    void generateArrays() {
        List<int[]> combinations = Combinations.generate(5, 2);
        assertThat(combinations)
                .hasSize(10)
                .containsExactly(
                        new int[]{0, 1},
                        new int[]{0, 2},
                        new int[]{0, 3},
                        new int[]{0, 4},
                        new int[]{1, 2},
                        new int[]{1, 3},
                        new int[]{1, 4},
                        new int[]{2, 3},
                        new int[]{2, 4},
                        new int[]{3, 4}
                );
    }

    @Test
    void generateLists() {
        List<List<String>> combinations = Combinations.generate(List.of("A", "B", "C", "D"), 2).toList();
        assertThat(combinations)
                .hasSize(6)
                .containsExactly(
                        List.of("A", "B"),
                        List.of("A", "C"),
                        List.of("A", "D"),
                        List.of("B", "C"),
                        List.of("B", "D"),
                        List.of("C", "D")
                );
    }

    @Test
    void testGenerateAll() {
        List<int[]> combinations = Combinations.generate(3, 3);
        assertThat(combinations).hasSize(1);
        assertThat(combinations.get(0)).containsExactly(0, 1, 2);
    }

    @Test
    void testGenerateNone() {
        List<int[]> combinations = Combinations.generate(3, 0);
        assertThat(combinations).hasSize(1);
        assertThat(combinations.get(0)).isEmpty();
    }
}