package com.adventofcode.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import com.adventofcode.test.AdventOfCode;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

@AdventOfCode(year = 2025, day = 5)
public class Day05Test {

    @Test
    void inputExample() {
        var input = """
                3-5
                10-14
                16-20
                12-18
                
                1
                5
                8
                11
                17
                32""";

        try (var scanner = new Scanner(input)) {
            int fresh = Day05.partOne(scanner);
            assertThat(fresh).isEqualTo(3);
        }
        try (var scanner = new Scanner(input)) {
            long size = Day05.partTwo(scanner);
            assertThat(size).isEqualTo(14);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int fresh = Day05.partOne(scanner);
        assertThat(fresh).isEqualTo(712);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long size = Day05.partTwo(scanner);
        assertThat(size).isEqualTo(332998283036769L);
    }
}
