package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test extends AbstractTest {

    protected Day02Test() {
        super(2015, 2);
    }

    @Test
    void inputExample() {
        assertThat(Day02.wrappingPaper("2x3x4")).isEqualTo(58);
        assertThat(Day02.wrappingPaper("1x1x10")).isEqualTo(43);
        assertThat(Day02.wrappingBow("2x3x4")).isEqualTo(34);
        assertThat(Day02.wrappingBow("1x1x10")).isEqualTo(14);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day02.wrappingPaper(scanner)).isEqualTo(1588178);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.wrappingBow(scanner)).isEqualTo(3783758);
    }
}
