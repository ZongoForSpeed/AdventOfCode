package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test extends AbstractTest {

    protected Day11Test() {
        super(2025, 11);
    }

    @Test
    void inputExampleOne() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.reactorPartOne(scanner);
            assertThat(result).isEqualTo(5);
        }
    }

    @Test
    void inputExampleTwo() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.reactorPartTwo(scanner);
            assertThat(result).isEqualTo(2);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        long result = Day11.reactorPartOne(scanner);
        assertThat(result).isEqualTo(701);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        long result = Day11.reactorPartTwo(scanner);
        assertThat(result).isEqualTo(390108778818526L);
    }

}
