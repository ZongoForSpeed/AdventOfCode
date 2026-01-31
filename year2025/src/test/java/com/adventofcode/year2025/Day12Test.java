package com.adventofcode.year2025;

import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test extends AbstractTest {

    protected Day12Test() {
        super(2025, 12);
    }

    @Test
    void inputExample() {
        String input = """
                0:
                ###
                ##.
                ##.
                
                1:
                ###
                ##.
                .##
                
                2:
                .##
                ###
                ##.
                
                3:
                ##.
                ###
                ##.
                
                4:
                ###
                #..
                ###
                
                5:
                ###
                .#.
                ###
                
                4x4: 0 0 0 0 2 0
                12x5: 1 0 1 0 2 2
                12x5: 1 0 1 0 3 2""";

        try (Scanner scanner = new Scanner(input)) {
            long solution = Day12.christmasTreeFarm(scanner);
            assertThat(solution).isEqualTo(3);
        }

    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        long solution = Day12.christmasTreeFarm(scanner);
        assertThat(solution).isEqualTo(531);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
    }

}
