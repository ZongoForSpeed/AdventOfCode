package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 5)
class Day05Test {

    @Test
    void inputExample() {
        var input = """
                47|53
                97|13
                97|61
                97|47
                75|29
                61|13
                75|53
                29|13
                97|29
                53|29
                61|53
                97|53
                61|29
                47|13
                75|47
                97|75
                47|61
                75|61
                47|29
                75|13
                53|13
                
                75,47,61,53,29
                97,61,53,29,13
                75,29,13
                75,97,47,61,53
                61,13,29
                97,13,75,29,47""";

        try (var scanner = new Scanner(input)) {
            int middle = Day05.partOne(scanner);
            assertThat(middle).isEqualTo(143);
        }

        try (var scanner = new Scanner(input)) {
            int middle = Day05.partTwo(scanner);
            assertThat(middle).isEqualTo(123);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day05.partOne(scanner)).isEqualTo(4637);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day05.partTwo(scanner)).isEqualTo(6370);
    }
}
