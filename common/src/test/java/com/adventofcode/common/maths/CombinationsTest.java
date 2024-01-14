package com.adventofcode.common.maths;

import com.adventofcode.common.maths.Combinations;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CombinationsTest {
    @Test
    void generateArrays() {
        List<int[]> combinations = Combinations.generate(5, 2);
        Assertions.assertThat(combinations)
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
        Assertions.assertThat(combinations)
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
}