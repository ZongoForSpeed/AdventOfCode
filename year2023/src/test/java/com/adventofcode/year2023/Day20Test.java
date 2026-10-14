package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@AdventOfCode(year = 2023, day = 20)
class Day20Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    @Test
    void inputExample1() {
        String input = """
                broadcaster -> a, b, c
                %a -> b
                %b -> c
                %c -> inv
                &inv -> a""";

        Scanner scanner = new Scanner(input);
        Map<Day20.Value, Long> counters = Day20.PartOne.pulsePropagation(scanner);
        Assertions.assertThat(counters).containsExactly(
                Map.entry(Day20.Value.HIGH, 4000L),
                Map.entry(Day20.Value.LOW, 8000L)
        );
        LOGGER.info("counters = {}", counters);
    }

    @Test
    void inputExample2() {
        String input = """
                broadcaster -> a
                %a -> inv, con
                &inv -> b
                %b -> con
                &con -> output""";

        Scanner scanner = new Scanner(input);
        Map<Day20.Value, Long> counters = Day20.PartOne.pulsePropagation(scanner);
        Assertions.assertThat(counters).containsExactly(
                Map.entry(Day20.Value.HIGH, 2750L),
                Map.entry(Day20.Value.LOW, 4250L)
        );
        LOGGER.info("counters = {}", counters);
    }

    @Test
    void inputPartOne() throws Exception {
        try (InputStream inputStream = Day20Test.class.getResourceAsStream("/2023/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {

        }
    }

    @Test
    void inputPartTwo() throws Exception {
        try (InputStream inputStream = Day20Test.class.getResourceAsStream("/2023/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {

        }
    }

    @Test
    void partOne(Scanner scanner) {
        Map<Day20.Value, Long> counters = Day20.PartOne.pulsePropagation(scanner);
        Long reduce = counters.values().stream().reduce(1L, (a, b) -> a * b);
        Assertions.assertThat(reduce).isEqualTo(788848550L);
    }

    @Test
    void partTwo(Scanner scanner) {
        long reduce = Day20.PartTwo.pulsePropagation(scanner);
        Assertions.assertThat(reduce).isEqualTo(228300182686739L);
    }

}
