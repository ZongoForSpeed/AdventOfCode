package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02Test extends AbstractTest {

    protected Day02Test() {
        super(2025, 2);
    }

    @Test
    void inputExample() {
        var input = """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124""";

        try (var scanner = new Scanner(input)) {
            var sum = Day02.partOne(scanner);
            assertThat(sum).isEqualTo(1227775554);
        }

        try (var scanner = new Scanner(input)) {
            var sum = Day02.partTwo(scanner);
            assertThat(sum).isEqualTo(4174379265L);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        var sum = Day02.partOne(scanner);
        assertThat(sum).isEqualTo(38437576669L);

    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        var sum = Day02.partTwo(scanner);
        assertThat(sum).isEqualTo(49046150754L);

    }
}
