package com.adventofcode.common.point.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InfiniteCharMapTest {

    @Test
    void testRead() {
        String input = """
                A.B
                ...
                C.D""";
        InfiniteCharMap map = InfiniteCharMap.read(input, c -> c != '.');
        assertThat(map).hasSize(4);
        assertThat(map.get(0, 0)).isEqualTo('A');
        assertThat(map.get(2, 0)).isEqualTo('B');
        assertThat(map.get(0, 2)).isEqualTo('C');
        assertThat(map.get(2, 2)).isEqualTo('D');
        assertThat(map.get(1, 1)).isNull();
    }

    @Test
    void testOperations() {
        InfiniteCharMap map = new InfiniteCharMap();
        map.put(100, 100, 'Z');
        assertThat(map.get(100, 100)).isEqualTo('Z');
        assertThat(map.containsKey(100, 100)).isTrue();
        
        map.remove(100, 100);
        assertThat(map.isEmpty()).isTrue();
    }

    @Test
    void testToString() {
        InfiniteCharMap map = new InfiniteCharMap();
        map.put(0, 0, 'X');
        map.put(1, 1, 'Y');
        // X.
        // .Y
        assertThat(map.toString()).isEqualTo("X.\n.Y");
    }
}
