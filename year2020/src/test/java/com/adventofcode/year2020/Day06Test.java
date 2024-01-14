package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {

    @Test
    void testCountUniqueQuestions() {
        List<String> group = List.of("abcx", "abcy", "abcz");

        assertThat(Day06.countUniqueQuestions(group)).isEqualTo(6);

        List<String> batch = List.of("abc", "", "a", "b", "c", "", "ab", "ac", "", "a", "a", "a", "a", "", "b");

        long sum = Day06.readGroups(batch).stream().map(Day06::countUniqueQuestions).mapToLong(t -> t).sum();
        assertThat(sum).isEqualTo(11);
    }

    @Test
    void testCountAllResponded() {
        List<String> batch = List.of("abc", "", "a", "b", "c", "", "ab", "ac", "", "a", "a", "a", "a", "", "b");

        long sum = Day06.readGroups(batch).stream().map(Day06::countAllResponded).mapToLong(t -> t).sum();
        assertThat(sum).isEqualTo(6);
    }

    @Test
    void inputCustomCustoms() throws IOException {
        List<List<String>> groups = Day06.readGroups(FileUtils.readLines("/2020/day/6/input"));
        long countUniqueQuestions = groups.stream().map(Day06::countUniqueQuestions).mapToLong(t -> t).sum();
        assertThat(countUniqueQuestions).isEqualTo(6799);

        long countAllResponded = groups.stream().map(Day06::countAllResponded).mapToLong(t -> t).sum();
        assertThat(countAllResponded).isEqualTo(3354);
    }
}
