package com.adventofcode.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import com.adventofcode.test.AbstractTest;
import java.util.OptionalLong;
import java.util.Scanner;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

public class Day08Test extends AbstractTest {

    protected Day08Test() {
        super(2025, 8);
    }

    @Test
    void inputExample() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            long result = Day08.partOne(scanner, 10);
            assertThat(result).isEqualTo(40);
        }

        try (Scanner scanner = new Scanner(input)) {
            OptionalLong result = Day08.partTwo(scanner);
            assertThat(result).hasValue(25272);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        long result = Day08.partOne(scanner, 1000);
        assertThat(result).isEqualTo(131150);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        OptionalLong result = Day08.partTwo(scanner);
        assertThat(result).hasValue(2497445);
    }
}
