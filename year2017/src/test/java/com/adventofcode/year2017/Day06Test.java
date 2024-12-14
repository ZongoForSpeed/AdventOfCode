package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test extends AbstractTest {
    Day06Test() {
        super(2017, 6);
    }

    @Test
    void inputExample() {
        assertThat(Day06.memoryReallocationPartOne("0 2 7 0")).isEqualTo(5);
        assertThat(Day06.memoryReallocationPartTwo("0 2 7 0")).isEqualTo(4);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.memoryReallocationPartOne(scanner.nextLine())).isEqualTo(11137);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.memoryReallocationPartTwo(scanner.nextLine())).isEqualTo(1037);
    }

}
