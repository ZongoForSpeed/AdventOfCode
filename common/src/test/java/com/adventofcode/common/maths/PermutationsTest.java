package com.adventofcode.common.maths;

import com.adventofcode.maths.Permutations;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class PermutationsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermutationsTest.class);

    @Test
    void permutations_abc() {
        List<String> abc = Permutations.permutations("abc");
        LOGGER.info("ABC = {}", abc);
        Assertions.assertThat(abc).containsExactly("abc", "acb", "bac", "bca", "cab", "cba");
    }

    @Test
    void permutations_abcd() {
        List<String> abcd = Permutations.permutations("abcd");
        LOGGER.info("ABCD = {}", abcd);
        Assertions.assertThat(abcd).containsExactly("abcd",
                "abdc",
                "acbd",
                "acdb",
                "adbc",
                "adcb",
                "bacd",
                "badc",
                "bcad",
                "bcda",
                "bdac",
                "bdca",
                "cabd",
                "cadb",
                "cbad",
                "cbda",
                "cdab",
                "cdba",
                "dabc",
                "dacb",
                "dbac",
                "dbca",
                "dcab",
                "dcba");
    }
}