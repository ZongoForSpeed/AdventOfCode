package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.LongPair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/8/input");
        int length = line.length();
        int size = 25 * 6;
        assertThat(length % size).isZero();

        LongPair pair = Day08.readImagePartOne(line, length, size);

        assertThat(pair.left()).isEqualTo(6);
        assertThat(pair.right()).isEqualTo(1935);
    }

    @Test
    void testExample() {
        int[] image = Day08.readImagePartTwo("0222112222120000", 4);
        assertThat(image).containsExactly(0, 1, 1, 0);
        printImage(image, 2, 2);
    }

    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/8/input");

        int[] image = Day08.readImagePartTwo(line, 25 * 6);
        assertThat(Arrays.stream(image).sum()).isEqualTo(51);
        printImage(image, 25, 6);
    }

    private void printImage(int[] image, int width, int height) {
        for (int index = 0; index < width * height; index += width) {
            StringBuilder sb = new StringBuilder();
            Arrays.spliterator(image, index, index + width).forEachRemaining((IntConsumer) i -> sb.append(i == 1 ? 'X' : ' '));
            LOGGER.debug("{}", sb);
        }
    }
}
