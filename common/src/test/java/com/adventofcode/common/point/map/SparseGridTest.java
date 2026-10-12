package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SparseGridTest {
    @Test
    void testMapOperations() {
        var map = new SparseGrid<Long>();
        var p1 = Point2D.of(1, 1);
        var p2 = Point2D.of(2, 2);
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
        var map = new SparseGrid<Long>();
        map.put(Point2D.of(0, 0), 1L);
        map.put(Point2D.of(1, 1), 2L);

        List<String> result = map.print(val -> val == 1L ? 'A' : 'B');
        assertThat(result).containsExactly("A ", " B");
    }

    @Test
    void testToStringUsesBoundingBox() {
        var map = new SparseGrid<Character>(c -> c, '.');
        map.put(Point2D.of(-1, 5), 'X');
        map.put(Point2D.of(1, 6), 'Y');

        assertThat(map).hasToString("X..\n..Y");
    }

    @Test
    void testEmptyGrid() {
        assertThat(new SparseGrid<Long>().print(_ -> 'X')).containsExactly(" ");
    }
}
