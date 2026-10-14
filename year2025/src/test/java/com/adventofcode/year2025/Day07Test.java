package com.adventofcode.year2025;

import com.adventofcode.common.utils.LongPair;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2025, day = 7)
public class Day07Test {

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

    @Test
    void partOne(Scanner scanner) throws Exception {
        LongPair result = Day07.countTimeline(scanner);
        assertThat(result.left()).isEqualTo(1587);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        LongPair result = Day07.countTimeline(scanner);
        assertThat(result.right()).isEqualTo(5748679033029L);
    }
}
