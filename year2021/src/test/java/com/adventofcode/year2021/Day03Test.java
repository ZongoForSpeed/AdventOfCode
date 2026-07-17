package com.adventofcode.year2021;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test extends AbstractTest {
    Day03Test() {
        super(2021, 3);
    }

    @Test
    void inputExample() {
        var strings = List.of(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010");

        IntegerPair p = Day03.binaryDiagnostic(strings);

        assertThat(p.left()).isEqualTo(22);
        assertThat(p.right()).isEqualTo(9);

        var oxygen = Day03.oxygenGeneratorRating(strings);
        var co2 = Day03.co2ScrubberRating(strings);
        assertThat(oxygen).isEqualTo(23);
        assertThat(co2).isEqualTo(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        var input = FileUtils.readLines(scanner);

        IntegerPair p = Day03.binaryDiagnostic(input);

        assertThat(p.left() * p.right()).isEqualTo(738234);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var input = FileUtils.readLines(scanner);

        var oxygen = Day03.oxygenGeneratorRating(input);
        var co2 = Day03.co2ScrubberRating(input);
        assertThat(oxygen * co2).isEqualTo(3969126);
    }
}
