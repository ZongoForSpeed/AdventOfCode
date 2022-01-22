package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    @Test
    void inputSimpleExample() {
        String input = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end""";

        {
            Set<List<String>> paths = Day12.findAllPathsPartOne(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(10);
        }

        {
            Set<List<String>> paths = Day12.findAllPathsPartTwo(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(36);
        }
    }

    @Test
    void inputSlightlyLargerExample() {
        String input = """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc""";


        {
            Set<List<String>> paths = Day12.findAllPathsPartOne(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(19);
        }

        {
            Set<List<String>> paths = Day12.findAllPathsPartTwo(new Scanner(input));
            List<String> sortedPaths = paths.stream().map(p -> String.join(",", p)).sorted().toList();
            for (String path : sortedPaths) {
                LOGGER.info("Path: {}", path);
            }

            assertThat(paths).hasSize(103);
        }
    }

    @Test
    void inputLargerExample() {
        String input = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW""";

        assertThat(Day12.findAllPathsPartOne(new Scanner(input))).hasSize(226);
        assertThat(Day12.findAllPathsPartTwo(new Scanner(input))).hasSize(3509);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2021/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day12.findAllPathsPartOne(scanner)).hasSize(5104);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2021/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day12.findAllPathsPartTwo(scanner)).hasSize(149220);
        }
    }

}
