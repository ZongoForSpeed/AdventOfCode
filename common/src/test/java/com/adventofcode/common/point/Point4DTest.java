package com.adventofcode.common.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Point4DTest {

    @Test
    void testOf() {
        Point4D p = Point4D.of(1, 2, 3, 4);
        assertThat(p.x()).isEqualTo(1);
        assertThat(p.y()).isEqualTo(2);
        assertThat(p.z()).isEqualTo(3);
        assertThat(p.w()).isEqualTo(4);
    }

    @Test
    void testManhattanDistance() {
        Point4D p1 = Point4D.of(1, 1, 1, 1);
        Point4D p2 = Point4D.of(4, 5, 6, 7);
        // |1-4| + |1-5| + |1-6| + |1-7| = 3 + 4 + 5 + 6 = 18
        assertThat(Point4D.manhattanDistance(p1, p2)).isEqualTo(18);
    }
}
