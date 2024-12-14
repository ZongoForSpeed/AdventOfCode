package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test extends AbstractTest {
    Day21Test() {
        super(2015, 21);
    }

    @Test
    void inputExample() {
        Day21.Player player = Day21.Player.of("player", 8, 5, 5);
        Day21.Player boss = Day21.Player.of("boss", 12, 7, 2);

        assertThat(Day21.fight(player, boss)).isTrue();
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day21.runFightsPartOne(scanner)).hasValue(111);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day21.runFightsPartTwo(scanner)).hasValue(188);
    }

}
