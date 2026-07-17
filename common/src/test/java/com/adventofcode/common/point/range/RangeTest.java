package com.adventofcode.common.point.range;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RangeTest {

    @Test
    void testOf() {
        Range r = Range.of(10, 20);
        assertThat(r.lower()).isEqualTo(10);
        assertThat(r.upper()).isEqualTo(20);

        Range r2 = Range.of("10..20");
        assertThat(r2).isEqualTo(r);
    }

    @Test
    void testIntersect() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(15, 25);
        Range r3 = Range.of(21, 30);

        assertThat(Range.intersect(r1, r2)).isTrue();
        assertThat(Range.intersect(r1, r3)).isFalse();
        assertThat(Range.intersect(r2, r3)).isTrue();
    }

    @Test
    void testIntersection() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(15, 25);
        Range r3 = Range.of(21, 30);

        assertThat(Range.intersection(r1, r2)).contains(Range.of(15, 20));
        assertThat(Range.intersection(r1, r3)).isEmpty();
    }

    @Test
    void testUnion() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(15, 25);
        assertThat(Range.union(r1, r2)).isEqualTo(Range.of(10, 25));
    }

    @Test
    void testContainsRange() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(12, 18);
        Range r3 = Range.of(5, 15);

        assertThat(r1.contains(r2)).isTrue();
        assertThat(r1.contains(r3)).isFalse();
    }

    @Test
    void testContainsLong() {
        Range r1 = Range.of(10, 20);
        assertThat(r1.contains(15)).isTrue();
        assertThat(r1.contains(10)).isTrue();
        assertThat(r1.contains(20)).isTrue();
        assertThat(r1.contains(5)).isFalse();
        assertThat(r1.contains(25)).isFalse();
    }

    @Test
    void testValid() {
        assertThat(Range.of(10, 20).valid()).isTrue();
        assertThat(Range.of(10, 10).valid()).isTrue();
        assertThat(Range.of(20, 10).valid()).isFalse();
    }

    @Test
    void testSize() {
        assertThat(Range.of(10, 20).size()).isEqualTo(11);
        assertThat(Range.of(10, 10).size()).isEqualTo(1);
    }

    @Test
    void testFullUnion() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(15, 25);
        
        // lowerMin=10, lowerMax=15, upperMin=20, upperMax=25
        // Expects: [10..14], [15..20], [21..25]
        var result = Range.fullUnion(r1, r2);
        assertThat(result).containsExactly(
                Range.of(10, 14),
                Range.of(15, 20),
                Range.of(21, 25)
        );
    }

    @Test
    void testDifference() {
        Range r1 = Range.of(10, 20);
        Range r2 = Range.of(15, 25);

        // r1 diff r2: [10..20] diff [15..25] should be [10..14]
        var result = Range.difference(r1, r2);
        assertThat(result).containsExactly(Range.of(10, 14));
    }

    @Test
    void testToString() {
        assertThat(Range.of(10, 20).toString()).isEqualTo("[10..20]");
    }
}
