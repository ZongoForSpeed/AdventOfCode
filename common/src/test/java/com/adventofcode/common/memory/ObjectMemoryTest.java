package com.adventofcode.common.memory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ObjectMemoryTest {

    @Test
    void testBasicOperations() {
        var memory = new ObjectMemory<String>(10);
        assertThat(memory.isEmpty()).isTrue();
        assertThat(memory.size()).isEqualTo(0);

        assertThat(memory.put(1, "A")).isNull();
        assertThat(memory.put(5, "E")).isNull();

        assertThat(memory.isEmpty()).isFalse();
        assertThat(memory.size()).isEqualTo(2);
        assertThat(memory.containsKey(1)).isTrue();
        assertThat(memory.get(1)).isEqualTo("A");
        assertThat(memory.get(2)).isNull();
    }

    @Test
    void testGetNonNull() {
        var memory = new ObjectMemory<String>(10);
        assertThat(memory.put(1, "A")).isNull();
        assertThat(memory.getNonNull(1)).isEqualTo("A");
        assertThatThrownBy(() -> memory.getNonNull(2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot find key: 2");
    }

    @Test
    void testPutReturnsOldValue() {
        var memory = new ObjectMemory<String>(10);
        assertThat(memory.put(1, "A")).isNull();
        assertThat(memory.put(1, "B")).isEqualTo("A");
        assertThat(memory.get(1)).isEqualTo("B");
    }

    @Test
    void testGrow() {
        var memory = new ObjectMemory<String>(2);
        assertThat(memory.put(10, "J")).isNull();
        assertThat(memory.get(10)).isEqualTo("J");
    }

    @Test
    void testClear() {
        var memory = new ObjectMemory<String>(10);
        assertThat(memory.put(1, "A")).isNull();
        memory.clear();
        assertThat(memory.isEmpty()).isTrue();
    }

    @Test
    void testKeySetAndValues() {
        var memory = new ObjectMemory<String>(10);
        assertThat(memory.put(1, "A")).isNull();
        assertThat(memory.put(2, "B")).isNull();

        assertThat(memory.keySet()).containsExactly(1, 2);
        assertThat(memory.values()).containsExactlyInAnyOrder("A", "B");
    }

    @Test
    void testNegativeKey() {
        var memory = new ObjectMemory<String>(10);
        assertThatThrownBy(() -> memory.get(-1))
                .isInstanceOf(IllegalStateException.class);
    }
}
