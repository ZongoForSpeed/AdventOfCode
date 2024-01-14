package com.adventofcode.common.map;

import com.adventofcode.common.point.map.BooleanMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanMapTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanMapTest.class);

    @Test
    void testBooleanMap() {
        BooleanMap map = BooleanMap.read("""
                                  #
                #    ##    ##    ###
                 #  #  #  #  #  #""", c -> c == '#');

        LOGGER.info("Map:\n{}", map);
        assertThat(map).hasToString("""
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
                """);

        assertThat(map.points()).hasSize(15);
        assertThat(map.cardinality()).isEqualTo(15);

        BooleanMap subMap = map.subMap(18, 20, 0, 2);
        assertThat(subMap).hasToString("""
                #.
                ##
                """).extracting(BooleanMap::cardinality).isEqualTo(3L);
    }

    @Test
    void rotate() {
        BooleanMap map = BooleanMap.read("""
                ..#
                ..#
                ###
                .#.""", c -> c == '#');

        BooleanMap rotate = map.rotate();
        assertThat(rotate).hasToString("""
                .#..
                ##..
                .###
                """);

        rotate = rotate.rotate();
        assertThat(rotate).hasToString("""
                .#.
                ###
                #..
                #..
                """);

        rotate = rotate.rotate();
        assertThat(rotate).hasToString("""
                ###.
                ..##
                ..#.
                """);
    }

}