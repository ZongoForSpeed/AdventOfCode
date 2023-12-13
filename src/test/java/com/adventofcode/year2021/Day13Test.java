package com.adventofcode.year2021;

import com.adventofcode.point.map.BooleanMap;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);
    @Test
    void inputExample() {
        String input = """
                6,10
                0,14
                9,10
                0,3
                10,4
                4,11
                6,0
                6,12
                4,1
                0,13
                10,12
                3,4
                3,0
                8,4
                1,10
                2,14
                8,10
                9,0

                fold along y=7
                fold along x=5""";


        Scanner scanner = new Scanner(input);

        BooleanMap map = new BooleanMap(0, 0);
        List<Pair<String, Integer>> instructions = new ArrayList<>();
        Day13.readMap(scanner, map, instructions);

        LOGGER.info("Map:\n{}", map);
        LOGGER.info("Instructions: {}", instructions);

        Day13.applyInstruction(map, instructions.get(0));
        LOGGER.info("Map:\n{}", map);
        Day13.applyInstruction(map, instructions.get(1));
        LOGGER.info("Map:\n{}", map);
        assertThat(map.points()).hasSize(16);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2021/day/13/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            BooleanMap map = Day13.transparentOrigamiPartOne(scanner);
            assertThat(map.cardinality()).isEqualTo(842);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day13Test.class.getResourceAsStream("/2021/day/13/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            BooleanMap map = Day13.transparentOrigamiPartTwo(scanner);
            assertThat(map.points()).hasSize(95);
        }
    }


}
