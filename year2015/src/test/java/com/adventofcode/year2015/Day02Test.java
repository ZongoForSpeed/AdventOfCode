package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 2)
class Day02Test {

    @Test
    void inputExample() {
        assertThat(Day02.wrappingPaper("2x3x4")).isEqualTo(58);
        assertThat(Day02.wrappingPaper("1x1x10")).isEqualTo(43);
        assertThat(Day02.wrappingBow("2x3x4")).isEqualTo(34);
        assertThat(Day02.wrappingBow("1x1x10")).isEqualTo(14);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day02.wrappingPaper(scanner)).isEqualTo(1588178);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day02.wrappingBow(scanner)).isEqualTo(3783758);
    }
}
