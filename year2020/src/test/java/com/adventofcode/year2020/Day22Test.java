package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test extends AbstractTest {
    Day22Test() {
        super(2020, 22);
    }

    @Test
    void testCrabCombat() {
        String input = """
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

        Scanner scanner = new Scanner(input);
        long point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(306);

        long recursiveCombat = Day22.playRecursiveCombat(new Scanner(input));
        assertThat(recursiveCombat).isEqualTo(291);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        long point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(33393);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        long recursiveCombat = Day22.playRecursiveCombat(scanner);
        assertThat(recursiveCombat).isEqualTo(31963);
    }
}
