package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day01Test extends AbstractTest {

    protected Day01Test() {
        super(2025, 1);
    }

    @Test
    void inputExample() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";

        try (var scanner = new Scanner(input)) {
            var password = Day01.countPasswordPartOne(scanner);

            assertThat(password).isEqualTo(3);
        }

        try (var scanner = new Scanner(input)) {
            var password = Day01.countPasswordPartTwo(scanner);

            assertThat(password).isEqualTo(6);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        var password = Day01.countPasswordPartOne(scanner);
        assertThat(password).isEqualTo(1031);
    }


    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        var password = Day01.countPasswordPartTwo(scanner);
        assertThat(password).isEqualTo(5831);
    }
}
