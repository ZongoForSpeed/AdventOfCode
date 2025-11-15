package com.adventofcode.common.map;

import com.adventofcode.common.point.map.CharMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CharMapTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharMapTest.class);
    @Test
    void charMap() {
        List<String> input = List.of(
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
        );

        CharMap map = new CharMap();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    map.set(j, i, line.charAt(j));
                }
            }
        }

        LOGGER.info("Map:\n{}", map);
        assertThat(map.points()).hasSize(15);
    }

}