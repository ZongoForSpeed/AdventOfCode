package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;

import org.junit.jupiter.api.Test;





import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 3)
class Day03Test {

    @Test
    void inputExample() {

        try (var scanner = new Scanner("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")){
            long results = Day03.partOne(scanner);
            assertThat(results).isEqualTo(161);
        }

        try (var scanner = new Scanner("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")){
            long results = Day03.partTwo(scanner);
            assertThat(results).isEqualTo(48);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day03.partOne(scanner)).isEqualTo(179571322L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day03.partTwo(scanner)).isEqualTo(103811193L);
    }

}
