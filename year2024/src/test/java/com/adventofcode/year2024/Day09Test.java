package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 9)
class Day09Test {

    @Test
    void inputExample1() {
        var input = "12345";

        assertThat(Day09.partOne(input)).isEqualTo(60);
    }

    @Test
    void inputExample2() {
        var input = "2333133121414131402";

        assertThat(Day09.partOne(input)).isEqualTo(1928);

        assertThat(Day09.partTwo(input)).isEqualTo(2858);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day09.partOne(scanner.nextLine())).isEqualTo(6360094256423L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day09.partTwo(scanner.nextLine())).isEqualTo(6379677752410L);
    }

}
