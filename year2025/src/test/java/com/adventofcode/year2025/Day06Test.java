package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day06Test extends AbstractTest {

    protected Day06Test() {
        super(2025, 6);
    }

    @Test
    void inputExample() {
        var input = """
                123 328  51 64 \s
                 45 64  387 23 \s
                  6 98  215 314\s
                *   +   *   +  \s""";

        try (var scanner = new Scanner(input)) {
            var result = Day06.partOne(scanner);
            assertThat(result).isEqualTo(4277556);
        }

        try (var scanner = new Scanner(input)) {
            var result = Day06.partTwo(scanner);
            assertThat(result).isEqualTo(3263827);
        }

    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        var result = Day06.partOne(scanner);
        assertThat(result).isEqualTo(5667835681547L);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        var result = Day06.partTwo(scanner);
        assertThat(result).isEqualTo(9434900032651L);
    }

}
