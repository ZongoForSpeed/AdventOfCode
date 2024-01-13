package com.adventofcode.year2021;

import com.adventofcode.point.map.CharMap;
import com.adventofcode.point.Point2D;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    @Test
    void simpleExample() {
        String input = """
                ...>...
                .......
                ......>
                v.....>
                ......>
                .......
                ..vvv..""";

        Scanner scanner = new Scanner(input);
        CharMap map = CharMap.read(scanner, c -> c != '.');

        int xMax = map.points().stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = map.points().stream().mapToInt(Point2D::y).max().orElseThrow();

        LOGGER.info("Initial state:\n{}", map);
        LOGGER.info("xMax: {}, yMax: {}", xMax, yMax);

        for (int step = 1; step <= 4; step++) {
            map = Day25.nextStep(map, xMax, yMax);
            LOGGER.info("After {} steps:\n{}", step, map);
        }

        assertThat(map.points()).hasSize(8);
    }

    @Test
    void inputExample() {
        String input = """
                v...>>.vv>
                .vv>>.vv..
                >>.>v>...v
                >>v>>.>.v.
                v>v.vv.v..
                >.>>..v...
                .vv..>.>v.
                v.v..>>v.v
                ....v..v.>
                """;

        Scanner scanner = new Scanner(input);
        int step = Day25.findLastStep(scanner);

        assertThat(step).isEqualTo(58);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day25Test.class.getResourceAsStream("/2021/day/25/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day25.findLastStep(scanner)).isEqualTo(367);
        }
    }

}
