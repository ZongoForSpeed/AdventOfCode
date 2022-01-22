package com.adventofcode.year2021;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void inputExample() {
        List<String> strings = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

        int[] array = strings.stream().mapToInt(Integer::valueOf).toArray();

        assertThat(Day01.measurementIncreases(array)).isEqualTo(7);
        assertThat(Day01.measurementIncreases(array, 3)).isEqualTo(5);
    }

    @Test
    void inputPartOne() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/1/input");
        int[] array = input.stream().mapToInt(Integer::valueOf).toArray();
        assertThat(Day01.measurementIncreases(array)).isEqualTo(1713);
    }

    @Test
    void inputPartTwo() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/1/input");
        int[] array = input.stream().mapToInt(Integer::valueOf).toArray();
        assertThat(Day01.measurementIncreases(array, 3)).isEqualTo(1734);
    }

}
