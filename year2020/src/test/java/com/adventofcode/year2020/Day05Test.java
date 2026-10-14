package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 5)
class Day05Test {
    @Test
    void binaryBoarding() {
        assertThat(Day05.binaryBoarding("FBFBBFFRLR")).isEqualTo(357);
        assertThat(Day05.binaryBoarding("BFFFBBFRRR")).isEqualTo(567);
        assertThat(Day05.binaryBoarding("FFFBBBFRRR")).isEqualTo(119);
        assertThat(Day05.binaryBoarding("BBFFBBFRLL")).isEqualTo(820);
    }

    @Test
    void partOne(Scanner scanner) {
        long[] seatIds = FileUtils.readLines(scanner)
                .stream()
                .map(Day05::binaryBoarding)
                .mapToLong(s -> s)
                .sorted().toArray();

        long max = Arrays.stream(seatIds).max().orElseThrow();
        assertThat(max).isEqualTo(933);
    }

    @Test
    void partTwo(Scanner scanner) {
        long[] seatIds = FileUtils.readLines(scanner)
                .stream()
                .map(Day05::binaryBoarding)
                .mapToLong(s -> s)
                .sorted().toArray();

        long emptySeat = Day05.findEmptySeat(seatIds).orElseThrow();
        assertThat(emptySeat).isEqualTo(711);
    }
}
