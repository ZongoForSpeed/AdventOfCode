package com.adventofcode.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import com.adventofcode.test.AbstractTest;
import java.util.Scanner;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

public class Day05Test extends AbstractTest {

    protected Day05Test() {
        super(2025, 5);
    }

    @Test
    void inputExample() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            int fresh = Day05.partOne(scanner);
            assertThat(fresh).isEqualTo(3);
        }
        try (Scanner scanner = new Scanner(input)) {
            long size = Day05.partTwo(scanner);
            assertThat(size).isEqualTo(14);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        int fresh = Day05.partOne(scanner);
        assertThat(fresh).isEqualTo(712);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        long size = Day05.partTwo(scanner);
        assertThat(size).isEqualTo(332998283036769L);
    }
}
