package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Map2DTest {

    @Test
    void testMapOperations() {
        Map2D map = new Map2D();
        Point2D p1 = Point2D.of(1, 1);
        Point2D p2 = Point2D.of(2, 2);

        map.put(p1, 100L);
        map.put(p2, 200L);

        assertThat(map).hasSize(2);
        assertThat(map.get(p1)).isEqualTo(100L);
        assertThat(map.containsKey(p1)).isTrue();
        assertThat(map.containsValue(200L)).isTrue();

        map.remove(p1);
        assertThat(map).hasSize(1);
        assertThat(map.get(p1)).isNull();
    }

    @Test
    void testPrint() {
        Map2D map = new Map2D();
        map.put(Point2D.of(0, 0), 1L);
        map.put(Point2D.of(1, 1), 2L);

        List<String> result = map.print(val -> val == 1L ? 'A' : 'B');
        
        assertThat(result).containsExactly(
                "A ",
                " B"
        );
    }

    @Test
    void testPrintWithView() {
        Map2D map = new Map2D();
        map.put(Point2D.of(0, 0), 1L);
        map.put(Point2D.of(1, 1), 2L);

        char[][] view = new char[2][2];
        for (char[] row : view) java.util.Arrays.fill(row, '.');

        List<String> result = map.print(view, val -> val == 1L ? 'A' : 'B');

        assertThat(result).containsExactly(
                "A.",
                ".B"
        );
    }
}
