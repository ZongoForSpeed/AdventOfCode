package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test extends AbstractTest {

    Day06Test() {
        super(2020, 6);
    }

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
    }

    @Override
    public void partOne(Scanner scanner) {
        List<List<String>> groups = Day06.readGroups(FileUtils.readLines(scanner));
        long countUniqueQuestions = groups.stream().map(Day06::countUniqueQuestions).mapToLong(t -> t).sum();
        assertThat(countUniqueQuestions).isEqualTo(6799);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<List<String>> groups = Day06.readGroups(FileUtils.readLines(scanner));
        long countAllResponded = groups.stream().map(Day06::countAllResponded).mapToLong(t -> t).sum();
        assertThat(countAllResponded).isEqualTo(3354);
    }
}
