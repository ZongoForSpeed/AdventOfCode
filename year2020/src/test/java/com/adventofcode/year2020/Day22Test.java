package com.adventofcode.year2020;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 22)
class Day22Test {
    @Test
    void crabCombat() {
        var input = """
                Player 1:
                9
                2
                6
                3
                1
                
                Player 2:
                5
                8
                4
                7
                10""";

        var scanner = new Scanner(input);
        long point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(306);

        long recursiveCombat = Day22.playRecursiveCombat(new Scanner(input));
        assertThat(recursiveCombat).isEqualTo(291);
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(33393);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long recursiveCombat = Day22.playRecursiveCombat(scanner);
        assertThat(recursiveCombat).isEqualTo(31963);
    }
}
