package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 1)
class Day01Test {

    @Test
    void inputExample() {
        var strings = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

        int[] array = strings.stream().mapToInt(Integer::valueOf).toArray();

        assertThat(Day01.measurementIncreases(array)).isEqualTo(7);
        assertThat(Day01.measurementIncreases(array, 3)).isEqualTo(5);
    }

    @Test
    void partOne(Scanner scanner) {
        IntList list = new IntArrayList();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        assertThat(Day01.measurementIncreases(list.toIntArray())).isEqualTo(1713);

    }

    @Test
    void partTwo(Scanner scanner) {
        IntList list = new IntArrayList();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        assertThat(Day01.measurementIncreases(list.toIntArray(), 3)).isEqualTo(1734);
    }

}
