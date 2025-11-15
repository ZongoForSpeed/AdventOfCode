package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test extends AbstractTest {
    Day05Test() {
        super(2020, 5);
    }

    @Test
    void binaryBoarding() {
        assertThat(Day05.binaryBoarding("FBFBBFFRLR")).isEqualTo(357);
        assertThat(Day05.binaryBoarding("BFFFBBFRRR")).isEqualTo(567);
        assertThat(Day05.binaryBoarding("FFFBBBFRRR")).isEqualTo(119);
        assertThat(Day05.binaryBoarding("BBFFBBFRLL")).isEqualTo(820);
    }

    @Override
    public void partOne(Scanner scanner) {
        long[] seatIds = FileUtils.readLines(scanner)
                .stream()
                .map(Day05::binaryBoarding)
                .mapToLong(s -> s)
                .sorted().toArray();

        long max = Arrays.stream(seatIds).max().orElseThrow();
        assertThat(max).isEqualTo(933);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long[] seatIds = FileUtils.readLines(scanner)
                .stream()
                .map(Day05::binaryBoarding)
                .mapToLong(s -> s)
                .sorted().toArray();

        long emptySeat = Day05.findEmptySeat(seatIds).orElseThrow();
        assertThat(emptySeat).isEqualTo(711);
    }
}
