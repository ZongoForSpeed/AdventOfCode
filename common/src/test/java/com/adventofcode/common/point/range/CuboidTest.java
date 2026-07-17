package com.adventofcode.common.point.range;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CuboidTest {

    @Test
    void testOf() {
        Cuboid c = Cuboid.of(0, 10, 0, 10, 0, 10);
        assertThat(c.dx()).isEqualTo(Range.of(0, 10));
        assertThat(c.dy()).isEqualTo(Range.of(0, 10));
        assertThat(c.dz()).isEqualTo(Range.of(0, 10));
    }

    @Test
    void testIntersect() {
        Cuboid c1 = Cuboid.of(0, 10, 0, 10, 0, 10);
        Cuboid c2 = Cuboid.of(5, 15, 5, 15, 5, 15);
        Cuboid c3 = Cuboid.of(11, 20, 11, 20, 11, 20);

        assertThat(Cuboid.intersect(c1, c2)).isTrue();
        assertThat(Cuboid.intersect(c1, c3)).isFalse();
    }

    @Test
    void testIntersection() {
        Cuboid c1 = Cuboid.of(0, 10, 0, 10, 0, 10);
        Cuboid c2 = Cuboid.of(5, 15, 5, 15, 5, 15);
        
        assertThat(Cuboid.intersection(c1, c2)).contains(Cuboid.of(5, 10, 5, 10, 5, 10));
    }

    @Test
    void testDifference() {
        Cuboid c1 = Cuboid.of(0, 2, 0, 2, 0, 2);
        Cuboid c2 = Cuboid.of(1, 1, 1, 1, 1, 1);
        
        // c1 size 27, c2 size 1. Difference size should be 26.
        var diff = Cuboid.difference(c1, c2);
        assertThat(diff.stream().mapToLong(Cuboid::size).sum()).isEqualTo(26);
    }

    @Test
    void testSize() {
        Cuboid c = Cuboid.of(0, 9, 0, 9, 0, 9);
        assertThat(c.size()).isEqualTo(1000);
    }

    @Test
    void testValid() {
        assertThat(Cuboid.of(0, 10, 0, 10, 0, 10).valid()).isTrue();
        assertThat(Cuboid.of(10, 0, 0, 10, 0, 10).valid()).isFalse();
    }
}
