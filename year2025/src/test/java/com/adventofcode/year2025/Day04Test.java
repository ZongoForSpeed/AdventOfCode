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
        var input = """
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

        try (var scanner = new Scanner(input)) {
            var size = Day04.partOne(scanner);
            assertThat(size).isEqualTo(13);
        }

        try (var scanner = new Scanner(input)) {
            var size = Day04.partTwo(scanner);
            assertThat(size).isEqualTo(43);
        }

    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        var size = Day04.partOne(scanner);
        assertThat(size).isEqualTo(1419);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        var size = Day04.partTwo(scanner);
        assertThat(size).isEqualTo(8739);
    }
}
