package com.adventofcode.common.memory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LongMemoryTest {

    @Test
    void testBasicOperations() {
        var memory = new LongMemory(10);
        assertThat(memory.isEmpty()).isTrue();
        assertThat(memory.size()).isEqualTo(0);

        assertThat(memory.put(1, 100L)).isNull();
        assertThat(memory.put(5, 500L)).isNull();

        assertThat(memory.isEmpty()).isFalse();
        assertThat(memory.size()).isEqualTo(2);
        assertThat(memory.containsKey(1)).isTrue();
        assertThat(memory.get(1)).isEqualTo(100L);
        assertThat(memory.get(2)).isNull();
    }

    @Test
    void testPutReturnsOldValue() {
        var memory = new LongMemory(10);
        assertThat(memory.put(1, 100L)).isNull();
        assertThat(memory.put(1, 200L)).isEqualTo(100L);
        assertThat(memory.get(1)).isEqualTo(200L);
    }

    @Test
    void testGrow() {
        var memory = new LongMemory(2);
        assertThat(memory.put(10, 1000L)).isNull();
        assertThat(memory.get(10)).isEqualTo(1000L);
    }

    @Test
    void testClear() {
        var memory = new LongMemory(10);
        assertThat(memory.put(1, 100L)).isNull();
        memory.clear();
        assertThat(memory.isEmpty()).isTrue();
    }

    @Test
    void testKeySetAndValues() {
        var memory = new LongMemory(10);
        assertThat(memory.put(1, 100L)).isNull();
        assertThat(memory.put(2, 200L)).isNull();

        assertThat(memory.keySet()).containsExactly(1, 2);
        assertThat(memory.values()).containsExactlyInAnyOrder(100L, 200L);
    }

    @Test
    void testNegativeKey() {
        var memory = new LongMemory(10);
        assertThatThrownBy(() -> memory.get(-1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testToString() {
        var memory = new LongMemory(10);
        assertThat(memory.put(1, 100L)).isNull();
        assertThat(memory.toString()).isEqualTo("{ (1 -> 100) }");
    }
}
