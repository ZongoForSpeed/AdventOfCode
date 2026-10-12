package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InfiniteBooleanMapTest {
    @Test
    void testSizeCountsCells() {
        var map = new InfiniteBooleanMap();
        map.put(Point2D.of(0, 0), true);
        map.put(Point2D.of(0, 1), false);
        map.put(Point2D.of(0, 1), true);
        map.put(Point2D.of(3, -2), true);
        assertThat(map).hasSize(3);

        assertThat(map.remove(Point2D.of(0, 1))).isTrue();
        assertThat(map.remove(Point2D.of(9, 9))).isNull();
        assertThat(map).hasSize(2);

        map.clear();
        assertThat(map).isEmpty();
    }

    @Test
    void testReadAndToString() {
        var map = InfiniteBooleanMap.read("#.\n.#", c -> c == '#');
        assertThat(map).hasSize(4);
        assertThat(map.get(Point2D.of(1, 1))).isTrue();
        assertThat(map).hasToString("#.\n.#");
    }
}
