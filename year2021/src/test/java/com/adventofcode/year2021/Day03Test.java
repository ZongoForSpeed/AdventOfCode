package com.adventofcode.year2021;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.IntegerPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void inputExample() {
        List<String> strings = List.of("00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010");

        IntegerPair p = Day03.binaryDiagnostic(strings);

        assertThat(p.left()).isEqualTo(22);
        assertThat(p.right()).isEqualTo(9);

        int oxygen = Day03.oxygenGeneratorRating(strings);
        int co2 = Day03.co2ScrubberRating(strings);
        assertThat(oxygen).isEqualTo(23);
        assertThat(co2).isEqualTo(10);
    }

    @Test
    void inputPartOne() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/3/input");

        IntegerPair p = Day03.binaryDiagnostic(input);

        assertThat(p.left() * p.right()).isEqualTo(738234);
    }

    @Test
    void inputPartTwo() throws IOException {
        List<String> input = FileUtils.readLines("/2021/day/3/input");

        int oxygen = Day03.oxygenGeneratorRating(input);
        int co2 = Day03.co2ScrubberRating(input);
        assertThat(oxygen * co2).isEqualTo(3969126);
    }
}
