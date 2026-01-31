package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day04Test extends AbstractTest {

    protected Day04Test() {
        super(2025, 4);
    }


    @Test
    void inputExample() {
        String input = """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.""";

        try (Scanner scanner = new Scanner(input)) {
            int size = Day04.partOne(scanner);
            assertThat(size).isEqualTo(13);
        }

        try (Scanner scanner = new Scanner(input)) {
            int size = Day04.partTwo(scanner);
            assertThat(size).isEqualTo(43);
        }

    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        int size = Day04.partOne(scanner);
        assertThat(size).isEqualTo(1419);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        int size = Day04.partTwo(scanner);
        assertThat(size).isEqualTo(8739);
    }
}
