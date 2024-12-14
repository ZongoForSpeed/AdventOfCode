package com.adventofcode.year2021;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test extends AbstractTest {

    Day01Test() {
        super(2021, 1);
    }

    @Test
    void inputExample() {
        List<String> strings = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

        int[] array = strings.stream().mapToInt(Integer::valueOf).toArray();

        assertThat(Day01.measurementIncreases(array)).isEqualTo(7);
        assertThat(Day01.measurementIncreases(array, 3)).isEqualTo(5);
    }

    @Override
    public void partOne(Scanner scanner) {
        int[] array = FileUtils.readLines(scanner).stream().mapToInt(Integer::valueOf).toArray();
        assertThat(Day01.measurementIncreases(array)).isEqualTo(1713);

    }

    @Override
    public void partTwo(Scanner scanner) {
        int[] array = FileUtils.readLines(scanner).stream().mapToInt(Integer::valueOf).toArray();
        assertThat(Day01.measurementIncreases(array, 3)).isEqualTo(1734);
    }

}
