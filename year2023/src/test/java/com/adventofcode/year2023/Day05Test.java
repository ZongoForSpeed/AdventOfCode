package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Scanner;

class Day05Test {

    @Test
    void exampleInput() {
        String input = """
                seeds: 79 14 55 13
                                
                seed-to-soil map:
                50 98 2
                52 50 48
                                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                                
                water-to-light map:
                88 18 7
                18 25 70
                                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                                
                humidity-to-location map:
                60 56 37
                56 93 4""";

        {
            Scanner scanner = new Scanner(input);

            OptionalLong min = Day05.PartOne.fertilizer(scanner);

            Assertions.assertThat(min).isPresent().hasValue(35);
        }

        {
            Scanner scanner = new Scanner(input);

            OptionalLong min = Day05.PartTwo.fertilizer(scanner);

            Assertions.assertThat(min).isPresent().hasValue(46);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day05Test.class.getResourceAsStream("/2023/day/05/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            OptionalLong min = Day05.PartOne.fertilizer(scanner);
            Assertions.assertThat(min).isPresent().hasValue(173706076L);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day05Test.class.getResourceAsStream("/2023/day/05/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            OptionalLong min = Day05.PartTwo.fertilizer(scanner);
            Assertions.assertThat(min).isPresent().hasValue(11611182L);
        }
    }


}
