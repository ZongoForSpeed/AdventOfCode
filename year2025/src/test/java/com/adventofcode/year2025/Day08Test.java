package com.adventofcode.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import com.adventofcode.test.AdventOfCode;
import java.util.OptionalLong;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

@AdventOfCode(year = 2025, day = 8)
public class Day08Test {

    @Test
    void inputExample() {
        var input = """
                162,817,812
                57,618,57
                906,360,560
                592,479,940
                352,342,300
                466,668,158
                542,29,236
                431,825,988
                739,650,466
                52,470,668
                216,146,977
                819,987,18
                117,168,530
                805,96,715
                346,949,466
                970,615,88
                941,993,340
                862,61,35
                984,92,344
                425,690,689""";

        try (var scanner = new Scanner(input)) {
            long result = Day08.partOne(scanner, 10);
            assertThat(result).isEqualTo(40);
        }

        try (var scanner = new Scanner(input)) {
            OptionalLong result = Day08.partTwo(scanner);
            assertThat(result).hasValue(25272);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long result = Day08.partOne(scanner, 1000);
        assertThat(result).isEqualTo(131150);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        OptionalLong result = Day08.partTwo(scanner);
        assertThat(result).hasValue(2497445);
    }
}
