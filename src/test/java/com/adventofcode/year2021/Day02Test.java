package com.adventofcode.year2021;

import com.adventofcode.utils.FileUtils;
import com.adventofcode.utils.IntegerPair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void inputExample() {
        List<String> strings = List.of("forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2");

        IntegerPair p = Day02.divePartOne(strings);

        assertThat(p.left()).isEqualTo(15);
        assertThat(p.right()).isEqualTo(10);

        Triple<Integer, Integer, Integer> triple = Day02.divePartTwo(strings);
        assertThat(triple).isEqualTo(Triple.of(15, 60, 10));

    }

    @Test
    void inputPartOne() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/2/input");

        IntegerPair p = Day02.divePartOne(input);

        assertThat(p.left() * p.right()).isEqualTo(1561344);
    }

    @Test
    void inputPartTwo() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/2/input");

        Triple<Integer, Integer, Integer> triple = Day02.divePartTwo(input);
        assertThat(triple).isEqualTo(Triple.of(2033, 909225, 768));
        assertThat(triple.getLeft() * triple.getMiddle()).isEqualTo(1848454425);
    }

}
