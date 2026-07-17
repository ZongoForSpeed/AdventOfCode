package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test extends AbstractTest {

    protected Day09Test() {
        super(2025, 9);
    }

    @Test
    void inputExample() {
        var input = """
                7,1
                11,1
                11,7
                9,7
                9,5
                2,5
                2,3
                7,3""";

        try (var scanner = new Scanner(input)) {
            assertEquals(50, Day09.partOne(scanner));
        }

        try (var scanner = new Scanner(input)) {
            assertEquals(24, Day09.partTwo(scanner));
        }

    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        assertEquals(4776100539L, Day09.partOne(scanner));
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        assertEquals(1476550548L, Day09.partTwo(scanner));
    }

}
