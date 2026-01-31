package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test extends AbstractTest {

    protected Day03Test() {
        super(2025, 3);
    }

    @Test
    void inputExample() {
        String input = """
                987654321111111
                811111111111119
                234234234234278
                818181911112111""";

        try (Scanner scanner = new Scanner(input)) {
            long sum = Day03.joltageLobby(scanner, 2);
            assertThat(sum).isEqualTo(357);
        }

        try (Scanner scanner = new Scanner(input)) {
            long sum = Day03.joltageLobby(scanner, 12);
            assertThat(sum).isEqualTo(3121910778619L);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {

        long sum = Day03.joltageLobby(scanner, 2);
        assertThat(sum).isEqualTo(17316);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {

        long sum = Day03.joltageLobby(scanner, 12);
        assertThat(sum).isEqualTo(171741365473332L);
    }
}
