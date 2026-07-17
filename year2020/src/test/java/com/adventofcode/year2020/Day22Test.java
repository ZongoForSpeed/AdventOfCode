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
        var point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(306);

        var recursiveCombat = Day22.playRecursiveCombat(new Scanner(input));
        assertThat(recursiveCombat).isEqualTo(291);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        var point = Day22.playCrabCombat(scanner);
        assertThat(point).isEqualTo(33393);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        var recursiveCombat = Day22.playRecursiveCombat(scanner);
        assertThat(recursiveCombat).isEqualTo(31963);
    }
}
