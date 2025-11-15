package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test extends AbstractTest {
    Day09Test() {
        super(2019, 9);
    }

    @Test
    void quine() {
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
    void output() {
        long result = Intcode.ioIntcode("104,1125899906842624,99", 0);
        assertThat(result).isEqualTo(1125899906842624L);
    }

    @Override
    public void partOne(Scanner scanner) {
        long result = Day09.sensorBoostPartOne(scanner.nextLine());
        assertThat(result).isEqualTo(3533056970L);

    }

    @Override
    public void partTwo(Scanner scanner) {
        long result = Day09.sensorBoostPartTwo(scanner.nextLine());
        assertThat(result).isEqualTo(72852L);
    }
}
