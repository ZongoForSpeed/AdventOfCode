package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class Day20Test {

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
        Day20.LOGGER.info("counters = {}", counters);
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
        Day20.LOGGER.info("counters = {}", counters);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day20Test.class.getResourceAsStream("/2023/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            Map<Day20.Value, Long> counters = Day20.PartOne.pulsePropagation(scanner);
            Long reduce = counters.values().stream().reduce(1L, (a, b) -> a * b);
            Assertions.assertThat(reduce).isEqualTo(788848550L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day20Test.class.getResourceAsStream("/2023/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long reduce = Day20.PartTwo.pulsePropagation(scanner);
            Assertions.assertThat(reduce).isEqualTo(228300182686739L);
        }
    }

}
