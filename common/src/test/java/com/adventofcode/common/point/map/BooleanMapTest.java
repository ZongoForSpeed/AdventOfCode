package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanMapTest {

    @Test
    void testRead() {
        String input = """
                #.#
                ...
                ###""";
        BooleanMap map = BooleanMap.read(input, c -> c == '#');
        assertThat(map.xMax()).isEqualTo(2);
        assertThat(map.yMax()).isEqualTo(2);
        assertThat(map.get(0, 0)).isTrue();
        assertThat(map.get(1, 0)).isFalse();
        assertThat(map.get(2, 0)).isTrue();
        assertThat(map.get(0, 2)).isTrue();
        assertThat(map.cardinality()).isEqualTo(5);
    }

    @Test
    void testSetReset() {
        BooleanMap map = new BooleanMap(10, 10);
        map.set(1, 1);
        assertThat(map.get(1, 1)).isTrue();
        map.reset(1, 1);
        assertThat(map.get(1, 1)).isFalse();
        
        map.set(Point2D.of(2, 2), true);
        assertThat(map.get(2, 2)).isTrue();
        map.set(Point2D.of(2, 2), false);
        assertThat(map.get(2, 2)).isFalse();
    }

    @Test
    void testRotateFlip() {
        String input = """
                #.
                ..""";
        BooleanMap map = BooleanMap.read(input, c -> c == '#');
        // map: (0,0) is #
        // sizeX=1, sizeY=1
        
        // rotate: (sizeY-y, x) -> (1-0, 0) -> (1,0)
        BooleanMap rotated = map.rotate();
        assertThat(rotated.get(1, 0)).isTrue();
        assertThat(rotated.get(0, 0)).isFalse();

        // flipX: (x, sizeY-y) -> (0, 1-0) -> (0,1)
        BooleanMap flipX = map.flipX(); 
        assertThat(flipX.get(0, 1)).isTrue();

        // flipY: (sizeX-x, y) -> (1-0, 0) -> (1,0)
        BooleanMap flipY = map.flipY(); 
        assertThat(flipY.get(1, 0)).isTrue();
    }

    @Test
    void testTrim() {
        BooleanMap map = new BooleanMap(10, 10);
        map.set(5, 5);
        map.trim();
        // xMax / yMax are the highest set indexes, as everywhere else in BooleanMap
        assertThat(map.xMax()).isEqualTo(5);
        assertThat(map.yMax()).isEqualTo(5);
        assertThat(map.get(5, 5)).isTrue();
    }

    @Test
    void testTrimEmptyMap() {
        var map = new BooleanMap(3, 3);
        map.trim();
        assertThat(map.xMax()).isZero();
        assertThat(map.yMax()).isZero();
        assertThat(map.cardinality()).isZero();
    }

    @Test
    void testEqualsIgnoresTrailingEmptyRows() {
        var small = new BooleanMap(2, 2);
        small.set(1, 1);
        var large = new BooleanMap(5, 5);
        large.set(1, 1);
        assertThat(large).isEqualTo(small).hasSameHashCodeAs(small);

        large.set(0, 4);
        assertThat(large).isNotEqualTo(small);
    }
}
