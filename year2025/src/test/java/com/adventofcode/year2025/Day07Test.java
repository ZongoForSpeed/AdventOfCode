package com.adventofcode.year2025;

import com.adventofcode.common.utils.LongPair;
import com.adventofcode.test.AbstractTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day07Test extends AbstractTest {

    protected Day07Test() {
        super(2025, 7);
    }

    @Test
    void inputExample() {
        var input = """
                .......S.......
                ...............
                .......^.......
                ...............
                ......^.^......
                ...............
                .....^.^.^.....
                ...............
                ....^.^...^....
                ...............
                ...^.^...^.^...
                ...............
                ..^...^.....^..
                ...............
                .^.^.^.^.^...^.
                ...............""";

        try (var scanner = new Scanner(input)) {
            LongPair result = Day07.countTimeline(scanner);
            assertThat(result.left()).isEqualTo(21);
            assertThat(result.right()).isEqualTo(40);
        }
    }

    @Override
    public void partOne(@NonNull Scanner scanner) throws Exception {
        LongPair result = Day07.countTimeline(scanner);
        assertThat(result.left()).isEqualTo(1587);
    }

    @Override
    public void partTwo(@NonNull Scanner scanner) throws Exception {
        LongPair result = Day07.countTimeline(scanner);
        assertThat(result.right()).isEqualTo(5748679033029L);
    }
}
