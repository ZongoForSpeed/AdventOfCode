package com.adventofcode.year2021;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test extends AbstractTest {
    Day02Test() {
        super(2021, 2);
    }

    @Test
    void inputExample() {
        List<String> strings = List.of("forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2");

        IntegerPair p = Day02.divePartOne(strings);

        assertThat(p.left()).isEqualTo(15);
        assertThat(p.right()).isEqualTo(10);

        Triple<Integer, Integer, Integer> triple = Day02.divePartTwo(strings);
        assertThat(triple).isEqualTo(Triple.of(15, 60, 10));

    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> input = FileUtils.readLines(scanner);

        IntegerPair p = Day02.divePartOne(input);
        assertThat(p.left() * p.right()).isEqualTo(1561344);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> input = FileUtils.readLines(scanner);

        Triple<Integer, Integer, Integer> triple = Day02.divePartTwo(input);
        assertThat(triple).isEqualTo(Triple.of(2033, 909225, 768));
        assertThat(triple.getLeft() * triple.getMiddle()).isEqualTo(1848454425);
    }

}
