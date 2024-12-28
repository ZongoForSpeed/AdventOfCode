package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test extends AbstractTest {

    protected Day15Test() {
        super(2020, 15);
    }

    @Test
    void testMemoryGame() {
        assertThat(Day15.memoryGame("0,3,6", 2020)).isEqualTo(436);
        assertThat(Day15.memoryGame("1,3,2", 2020)).isEqualTo(1);
        assertThat(Day15.memoryGame("2,1,3", 2020)).isEqualTo(10);
        assertThat(Day15.memoryGame("1,2,3", 2020)).isEqualTo(27);
        assertThat(Day15.memoryGame("2,3,1", 2020)).isEqualTo(78);
        assertThat(Day15.memoryGame("3,2,1", 2020)).isEqualTo(438);
        assertThat(Day15.memoryGame("3,1,2", 2020)).isEqualTo(1836);

        assertThat(Day15.memoryGame("0,3,6", 30000000)).isEqualTo(175594);
        assertThat(Day15.memoryGame("1,3,2", 30000000)).isEqualTo(2578);
        assertThat(Day15.memoryGame("2,1,3", 30000000)).isEqualTo(3544142);
        assertThat(Day15.memoryGame("1,2,3", 30000000)).isEqualTo(261214);
        assertThat(Day15.memoryGame("2,3,1", 30000000)).isEqualTo(6895259);
        assertThat(Day15.memoryGame("3,2,1", 30000000)).isEqualTo(18);
        assertThat(Day15.memoryGame("3,1,2", 30000000)).isEqualTo(362);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        assertThat(Day15.memoryGame(scanner.nextLine(), 2020)).isEqualTo(866);

    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        assertThat(Day15.memoryGame(scanner.nextLine(), 30000000)).isEqualTo(1437692);
    }
}
