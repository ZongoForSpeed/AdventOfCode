package com.adventofcode.common.memory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryTest {

    @Test
    void testDefaultGetOrDefault() {
        Memory<Integer> memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();

        assertThat(memory.getOrDefault(1, 0)).isEqualTo(100);
        assertThat(memory.getOrDefault(2, 0)).isEqualTo(0);
    }

    @Test
    void testDefaultPutIfAbsent() {
        Memory<Integer> memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();

        assertThat(memory.putIfAbsent(1, 200)).isEqualTo(100);
        assertThat(memory.get(1)).isEqualTo(100);

        assertThat(memory.putIfAbsent(2, 200)).isNull();
        assertThat(memory.get(2)).isEqualTo(200);
    }

    @Test
    void testDefaultReplace() {
        Memory<Integer> memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();

        assertThat(memory.replace(1, 100, 200)).isTrue();
        assertThat(memory.get(1)).isEqualTo(200);

        assertThat(memory.replace(1, 100, 300)).isFalse();
        assertThat(memory.get(1)).isEqualTo(200);

        assertThat(memory.replace(2, null, 200)).isFalse();
    }

    @Test
    void testDefaultComputeIfAbsent() {
        Memory<Integer> memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();

        assertThat(memory.computeIfAbsent(1, k -> k * 10)).isEqualTo(100);
        assertThat(memory.computeIfAbsent(2, k -> k * 10)).isEqualTo(20);
        assertThat(memory.get(2)).isEqualTo(20);
    }
}
