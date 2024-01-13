package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    @Test
    void testHandyHaversacks1() {
        List<String> rules = List.of("light red bags contain 1 bright white bag, 2 muted yellow bags.",
                "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
                "bright white bags contain 1 shiny gold bag.",
                "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
                "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
                "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
                "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
                "faded blue bags contain no other bags.",
                "dotted black bags contain no other bags.");

        rules.forEach(s -> LOGGER.info("rule: {}", Day07.readRule(s)));

        assertThat(Day07.countShinyGold(rules)).isEqualTo(4);

        assertThat(Day07.countHandyHaversacks(rules)).isEqualTo(32);
    }

    @Test
    void testHandyHaversacks2() {
        List<String> rules = List.of("shiny gold bags contain 2 dark red bags.",
                "dark red bags contain 2 dark orange bags.",
                "dark orange bags contain 2 dark yellow bags.",
                "dark yellow bags contain 2 dark green bags.",
                "dark green bags contain 2 dark blue bags.",
                "dark blue bags contain 2 dark violet bags.",
                "dark violet bags contain no other bags.");

        assertThat(Day07.countHandyHaversacks(rules)).isEqualTo(126);
    }

    @Test
    void inputHandyHaversacks() throws IOException {
        List<String> rules = FileUtils.readLines("/2020/day/7/input");

        assertThat(Day07.countShinyGold(rules)).isEqualTo(224);

        assertThat(Day07.countHandyHaversacks(rules)).isEqualTo(1488);
    }
}
