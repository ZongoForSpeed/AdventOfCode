package com.adventofcode.map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanMapTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanMapTest.class);

    @Test
    void testCharMap() {
        BooleanMap map = BooleanMap.read("""
                                  # \s
                #    ##    ##    ###\s
                 #  #  #  #  #  #   \s""", c -> c == '#');

        LOGGER.info("Map:\n{}", map);
        assertThat(map).hasToString("""
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
                """);

        assertThat(map.points()).hasSize(15);
        assertThat(map.cardinality()).isEqualTo(15);
    }

}