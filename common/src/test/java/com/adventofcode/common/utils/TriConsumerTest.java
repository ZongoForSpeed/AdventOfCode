package com.adventofcode.common.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TriConsumerTest {

    @Test
    void testTriConsumer() {
        List<String> result = new ArrayList<>();
        TriConsumer<String, Integer, Boolean> triConsumer = (s, i, b) -> {
            result.add(s + i + b);
        };

        triConsumer.accept("test", 1, true);
        assertThat(result).containsExactly("test1true");
    }
}
