package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

    @Test
    void testBinaryBoarding() {
        assertThat(Day05.binaryBoarding("FBFBBFFRLR")).isEqualTo(357);
        assertThat(Day05.binaryBoarding("BFFFBBFRRR")).isEqualTo(567);
        assertThat(Day05.binaryBoarding("FFFBBBFRRR")).isEqualTo(119);
        assertThat(Day05.binaryBoarding("BBFFBBFRLL")).isEqualTo(820);
    }

    @Test
    void imputBinaryBoarding() throws IOException {
        long[] seatIds = FileUtils.readLines("/2020/day/5/input")
                .stream()
                .map(Day05::binaryBoarding)
                .mapToLong(s -> s)
                .sorted().toArray();

        long max = Arrays.stream(seatIds).max().orElseThrow();
        assertThat(max).isEqualTo(933);

        long emptySeat = Day05.findEmptySeat(seatIds).orElseThrow();
        assertThat(emptySeat).isEqualTo(711);
    }
}
