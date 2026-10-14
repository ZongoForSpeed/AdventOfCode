package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CharMapTest {

    @Test
    void testReadAndGet() {
        String input = """
                ABC
                DEF""";
        CharMap map = CharMap.read(input, c -> true);
        assertThat(map.xMax()).isEqualTo(2);
        assertThat(map.yMax()).isEqualTo(1);
        assertThat(map.get(0, 0)).isEqualTo('A');
        assertThat(map.get(2, 1)).isEqualTo('F');
    }

    @Test
    void testSetAndReset() {
        CharMap map = new CharMap(5, 5, ' ');
        map.set(1, 1, 'X');
        assertThat(map.get(1, 1)).isEqualTo('X');
        map.reset(1, 1);
        assertThat(map.get(1, 1)).isEqualTo(' ');
    }

    @Test
    void testInsert() {
        CharMap map = CharMap.read("AB\nCD", c -> true);
        // AB
        // CD
        map.insertLine(1, '.');
        // AB
        // ..
        // CD
        assertThat(map.yMax()).isEqualTo(2);
        assertThat(map.get(0, 2)).isEqualTo('C');
        assertThat(map.get(0, 1)).isEqualTo('.');

        map.insertColumn(1, '|');
        // A|B
        // .|.
        // C|D
        assertThat(map.xMax()).isEqualTo(2);
        assertThat(map.get(2, 0)).isEqualTo('B');
        assertThat(map.get(1, 0)).isEqualTo('|');
    }

    @Test
    void testPointsAndEntries() {
        CharMap map = CharMap.read("A", c -> true);
        List<Point2D> points = map.points();
        assertThat(points).containsExactly(Point2D.of(0, 0));
        
        assertThat(map.entries()).hasSize(1);
        assertThat(map.entries().getFirst().left()).isEqualTo(Point2D.of(0, 0));
        assertThat(map.entries().getFirst().rightChar()).isEqualTo('A');
    }

    @Test
    void testInsertLineAtEnd() {
        var map = CharMap.read("ab\ncd", _ -> true);
        map.insertLine(2, '#');
        assertThat(map.values()).containsExactly("ab", "cd", "##");
    }

    @Test
    void testSubMapHasRequestedSize() {
        var map = CharMap.read("abc\ndef\nghi", _ -> true);
        var subMap = map.subMap(1, 3, 0, 2);
        assertThat(subMap.values()).containsExactly("bc", "ef");
    }

    @Test
    void charMap() {
        var input = List.of(
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
        );

        var map = new CharMap();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    map.set(j, i, line.charAt(j));
                }
            }
        }

        assertThat(map.points()).hasSize(15);
    }
}
