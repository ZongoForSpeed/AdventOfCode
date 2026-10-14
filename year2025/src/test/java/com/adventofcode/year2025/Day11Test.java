package com.adventofcode.year2025;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 11)
public class Day11Test {

    @Test
    void inputExampleOne() {
        var input = """
                aaa: you hhh
                you: bbb ccc
                bbb: ddd eee
                ccc: ddd eee fff
                ddd: ggg
                eee: out
                fff: out
                ggg: out
                hhh: ccc fff iii
                iii: out""";

        try (var scanner = new Scanner(input)) {
            long result = Day11.reactorPartOne(scanner);
            assertThat(result).isEqualTo(5);
        }
    }

    @Test
    void inputExampleTwo() {
        var input = """
                svr: aaa bbb
                aaa: fft
                fft: ccc
                bbb: tty
                tty: ccc
                ccc: ddd eee
                ddd: hub
                hub: fff
                eee: dac
                dac: fff
                fff: ggg hhh
                ggg: out
                hhh: out""";

        try (var scanner = new Scanner(input)) {
            long result = Day11.reactorPartTwo(scanner);
            assertThat(result).isEqualTo(2);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long result = Day11.reactorPartOne(scanner);
        assertThat(result).isEqualTo(701);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long result = Day11.reactorPartTwo(scanner);
        assertThat(result).isEqualTo(390108778818526L);
    }

}
