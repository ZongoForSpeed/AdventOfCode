package com.adventofcode.common.point.range;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RectangleTest {

    @Test
    void testOf() {
        Rectangle r = Rectangle.of(0, 10, 0, 10);
        assertThat(r.dx()).isEqualTo(Range.of(0, 10));
        assertThat(r.dy()).isEqualTo(Range.of(0, 10));
    }

    @Test
    void testIntersect() {
        Rectangle r1 = Rectangle.of(0, 10, 0, 10);
        Rectangle r2 = Rectangle.of(5, 15, 5, 15);
        Rectangle r3 = Rectangle.of(11, 20, 11, 20);

        assertThat(Rectangle.intersect(r1, r2)).isTrue();
        assertThat(Rectangle.intersect(r1, r3)).isFalse();
    }

    @Test
    void testIntersection() {
        Rectangle r1 = Rectangle.of(0, 10, 0, 10);
        Rectangle r2 = Rectangle.of(5, 15, 5, 15);
        
        assertThat(Rectangle.intersection(r1, r2)).contains(Rectangle.of(5, 10, 5, 10));
    }

    @Test
    void testContains() {
        Rectangle r1 = Rectangle.of(0, 10, 0, 10);
        Rectangle r2 = Rectangle.of(2, 8, 2, 8);
        
        assertThat(r1.contains(r2)).isTrue();
        assertThat(r2.contains(r1)).isFalse();
    }

    @Test
    void testSize() {
        Rectangle r = Rectangle.of(0, 9, 0, 9);
        assertThat(r.size()).isEqualTo(100);
    }

    @Test
    void testDifference() {
        Rectangle r1 = Rectangle.of(0, 2, 0, 2);
        Rectangle r2 = Rectangle.of(1, 1, 1, 1);
        
        // r1: [0..2]x[0..2], r2: [1..1]x[1..1]
        // r1 size is 9, r2 size is 1. Intersection is r2.
        // Difference should be 8 unit rectangles.
        List<Rectangle> diff = Rectangle.difference(r1, r2);
        assertThat(diff.stream().mapToLong(Rectangle::size).sum()).isEqualTo(8);
        assertThat(diff).allMatch(r -> r1.contains(r) && !Rectangle.intersect(r, r2));
    }

    @Test
    void testUnion() {
        Rectangle r1 = Rectangle.of(0, 1, 0, 1);
        Rectangle r2 = Rectangle.of(1, 2, 1, 2);
        
        // r1 is [0,1]x[0,1], r2 is [1,2]x[1,2]. Intersection is [1,1]x[1,1]
        // r1 size 4, r2 size 4, intersection size 1. Union size should be 7.
        List<Rectangle> union = Rectangle.union(r1, r2);
        assertThat(union.stream().mapToLong(Rectangle::size).sum()).isEqualTo(7);
    }

    @Test
    void testValid() {
        assertThat(Rectangle.of(0, 10, 0, 10).valid()).isTrue();
        assertThat(Rectangle.of(10, 0, 0, 10).valid()).isFalse();
    }
}
