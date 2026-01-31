package com.adventofcode.common.maths;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PermutationsTest {

    @Test
    void permutations_abc() {
        List<String> abc = Permutations.permutations("abc");
        assertThat(abc).containsExactly("abc", "acb", "bac", "bca", "cab", "cba");
    }

    @Test
    void testFactorial() {
        assertThat(Permutations.factorial(0)).isEqualTo(1);
        assertThat(Permutations.factorial(1)).isEqualTo(1);
        assertThat(Permutations.factorial(5)).isEqualTo(120);
        assertThat(Permutations.factorial(20)).isEqualTo(2432902008176640000L);
    }

    @Test
    void testPermutationByNumber() {
        List<String> items = List.of("A", "B", "C");
        assertThat(Permutations.permutation(0, items)).containsExactly("A", "B", "C");
        assertThat(Permutations.permutation(5, items)).containsExactly("C", "B", "A");
    }

    @Test
    void testOfObjects() {
        List<List<String>> result = Permutations.ofObjects("A", "B", "C").toList();
        assertThat(result).containsExactly(
                List.of("A", "B", "C"),
                List.of("A", "C", "B"),
                List.of("B", "A", "C"),
                List.of("B", "C", "A"),
                List.of("C", "A", "B"),
                List.of("C", "B", "A")
        );
    }

    @Test
    void testOfInts() {
        List<IntList> result = Permutations.ofInts(1, 2, 3).toList();
        assertThat(result).containsExactly(
                IntList.of(1, 2, 3),
                IntList.of(1, 3, 2),
                IntList.of(2, 1, 3),
                IntList.of(2, 3, 1),
                IntList.of(3, 1, 2),
                IntList.of(3, 2, 1)
        );
    }

    @Test
    void testOfLongs() {
        List<LongList> result = Permutations.ofLongs(1L, 2L).toList();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(LongList.of(1, 2), LongList.of(2, 1));
    }
}