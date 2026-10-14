package com.adventofcode.year2020;

import com.adventofcode.test.AdventOfCode;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 1)
class Day01Test {
    @Test
    void reportRepair() {
        long[] report = new long[]{
                1721L, 979L, 366L, 299L, 675L, 1456L
        };

        assertThat(Day01.reportRepair2(report)).isEqualTo(514579);
        assertThat(Day01.reportRepair3(report)).isEqualTo(241861950);
    }

    @Test
    void partOne(Scanner scanner) {
        LongList report = new LongArrayList();
        while (scanner.hasNextLong()) {
            report.add(scanner.nextLong());
        }
        assertThat(Day01.reportRepair2(report.toLongArray())).isEqualTo(1016964);
    }

    @Test
    void partTwo(Scanner scanner) {
        LongList report = new LongArrayList();
        while (scanner.hasNextLong()) {
            report.add(scanner.nextLong());
        }

        assertThat(Day01.reportRepair3(report.toLongArray())).isEqualTo(182588480);
    }
}
