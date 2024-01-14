package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test {

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/19/input");
        assertThat(Day19.countPartOne(line)).isEqualTo(166);
    }

    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/19/input");
        assertThat(Day19.countPartTwo(line)).isEqualTo(3790981);
    }

}
