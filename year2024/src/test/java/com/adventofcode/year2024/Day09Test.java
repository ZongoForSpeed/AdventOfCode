package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test extends AbstractTest {

    protected Day09Test() {
        super(2024, 9);
    }

    @Test
    void inputExample1() {
        String input = "12345";

        assertThat(Day09.partOne(input)).isEqualTo(60);
    }

    @Test
    void inputExample2() {
        String input = "2333133121414131402";

        assertThat(Day09.partOne(input)).isEqualTo(1928);

        assertThat(Day09.partTwo(input)).isEqualTo(2858);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day09.partOne(scanner.nextLine())).isEqualTo(6360094256423L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day09.partTwo(scanner.nextLine())).isEqualTo(6379677752410L);
    }

}
