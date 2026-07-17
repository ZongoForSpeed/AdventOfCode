package com.adventofcode.common.memory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IntMemoryTest {

    @Test
    void testBasicOperations() {
        var memory = new IntMemory(10);
        assertThat(memory.isEmpty()).isTrue();
        assertThat(memory.size()).isEqualTo(0);

        assertThat(memory.put(1, 100)).isNull();
        assertThat(memory.put(5, 500)).isNull();
        
        assertThat(memory.isEmpty()).isFalse();
        assertThat(memory.size()).isEqualTo(2);
        assertThat(memory.containsKey(1)).isTrue();
        assertThat(memory.containsKey(5)).isTrue();
        assertThat(memory.containsKey(2)).isFalse();

        assertThat(memory.get(1)).isEqualTo(100);
        assertThat(memory.get(5)).isEqualTo(500);
        assertThat(memory.get(2)).isNull();
    }

    @Test
    void testPutReturnsOldValue() {
        var memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();
        assertThat(memory.put(1, 200)).isEqualTo(100);
        assertThat(memory.get(1)).isEqualTo(200);
    }

    @Test
    void testIncrement() {
        var memory = new IntMemory(10);
        assertThat(memory.increment(1, 10)).isNull();
        assertThat(memory.get(1)).isEqualTo(10);

        assertThat(memory.increment(1, 5)).isEqualTo(10);
        assertThat(memory.get(1)).isEqualTo(15);
    }

    @Test
    void testGrow() {
        var memory = new IntMemory(2);
        assertThat(memory.put(1, 100)).isNull();
        assertThat(memory.put(2, 200)).isNull();
        // This should trigger grow
        assertThat(memory.put(10, 1000)).isNull();

        assertThat(memory.get(1)).isEqualTo(100);
        assertThat(memory.get(2)).isEqualTo(200);
        assertThat(memory.get(10)).isEqualTo(1000);
        assertThat(memory.size()).isEqualTo(3);
    }

    @Test
    void testClear() {
        var memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();
        memory.clear();

        assertThat(memory.isEmpty()).isTrue();
        assertThat(memory.get(1)).isNull();
    }

    @Test
    void testKeySetAndValues() {
        var memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();
        assertThat(memory.put(3, 300)).isNull();
        assertThat(memory.put(2, 200)).isNull();

        assertThat(memory.keySet()).containsExactly(1, 2, 3);
        assertThat(memory.values()).containsExactlyInAnyOrder(100, 200, 300);
    }

    @Test
    void testNegativeKey() {
        var memory = new IntMemory(10);
        assertThatThrownBy(() -> memory.get(-1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Negative key are not allowed");
    }

    @Test
    void testToString() {
        var memory = new IntMemory(10);
        assertThat(memory.put(1, 100)).isNull();
        assertThat(memory.put(2, 200)).isNull();

        assertThat(memory.toString()).isEqualTo("{ (1 -> 100), (2 -> 200) }");
    }
}
