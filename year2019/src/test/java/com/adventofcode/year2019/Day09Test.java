package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.common.utils.FileUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {
    @Test
    void testQuine() {
        LongList output = new LongArrayList();
        Intcode.intcode("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99", () -> 0, output::add);
        String result = output.longStream().mapToObj(Objects::toString).collect(Collectors.joining(","));
        assertThat(result).isEqualTo("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99");
    }

    @Test
    void test16Number() {
        long result = Intcode.ioIntcode("1102,34915192,34915192,7,4,7,99,0", 0);
        assertThat(result).isEqualTo(1219070632396864L);
    }

    @Test
    void testOutput() {
        long result = Intcode.ioIntcode("104,1125899906842624,99", 0);
        assertThat(result).isEqualTo(1125899906842624L);
    }

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/9/input");
        long result = Day09.sensorBoostPartOne(line);
        assertThat(result).isEqualTo(3533056970L);
    }


    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/9/input");
        long result = Day09.sensorBoostPartTwo(line);
        assertThat(result).isEqualTo(72852L);
    }

}
