package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test extends AbstractTest {
    Day01Test() {
        super(2020, 1);
    }

    @Test
    void testReportRepair() {
        long[] report = new long[]{
                1721L, 979L, 366L, 299L, 675L, 1456L
        };

        assertThat(Day01.reportRepair2(report)).isEqualTo(514579);
        assertThat(Day01.reportRepair3(report)).isEqualTo(241861950);
    }

    @Override
    public void partOne(Scanner scanner) {
        LongList report = new LongArrayList();
        while (scanner.hasNextLong()) {
            report.add(scanner.nextLong());
        }
        assertThat(Day01.reportRepair2(report.toLongArray())).isEqualTo(1016964);
    }

    @Override
    public void partTwo(Scanner scanner) {
        LongList report = new LongArrayList();
        while (scanner.hasNextLong()) {
            report.add(scanner.nextLong());
        }

        assertThat(Day01.reportRepair3(report.toLongArray())).isEqualTo(182588480);
    }
}
