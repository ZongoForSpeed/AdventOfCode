package com.adventofcode.year2019;

import com.adventofcode.common.utils.LongPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test extends AbstractTest {
    Day08Test() {
        super(2019, 8);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    @Test
    void example() {
        int[] image = Day08.readImagePartTwo("0222112222120000", 4);
        assertThat(image).containsExactly(0, 1, 1, 0);
        printImage(image, 2, 2);
    }

    private void printImage(int[] image, int width, int height) {
        for (int index = 0; index < width * height; index += width) {
            StringBuilder sb = new StringBuilder();
            Arrays.spliterator(image, index, index + width).forEachRemaining((IntConsumer) i -> sb.append(i == 1 ? 'X' : ' '));
            LOGGER.debug("{}", sb);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        int length = line.length();
        int size = 25 * 6;
        assertThat(length % size).isZero();

        LongPair pair = Day08.readImagePartOne(line, length, size);

        assertThat(pair.left()).isEqualTo(6);
        assertThat(pair.right()).isEqualTo(1935);
    }

    @Override
    public void partTwo(Scanner scanner) {
        String line = scanner.nextLine();

        int[] image = Day08.readImagePartTwo(line, 25 * 6);
        assertThat(Arrays.stream(image).sum()).isEqualTo(51);
        printImage(image, 25, 6);
    }
}
