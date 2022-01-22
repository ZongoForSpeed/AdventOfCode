package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void testReportRepair() {
        long[] report = new long[] {
                1721L, 979L, 366L, 299L, 675L, 1456L
        };

        assertThat(Day01.reportRepair2(report)).isEqualTo(514579);
        assertThat(Day01.reportRepair3(report)).isEqualTo(241861950);

    }

    @Test
    void inputReportRepair() throws IOException {
        List<String> input = FileUtils.readLines("/2020/day/1/input");
        long[] report = input.stream().mapToLong(Long::valueOf).toArray();

        assertThat(Day01.reportRepair2(report)).isEqualTo(1016964);
        assertThat(Day01.reportRepair3(report)).isEqualTo(182588480);
    }

}
