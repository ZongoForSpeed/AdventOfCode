package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day22Test extends AbstractTest {
    Day22Test() {
        super(2015, 22);
    }

    @Test
    void inputSmallExample() {
        Day22.Player player = Day22.Player.of(10, 250, new EnumMap<>(Day22.Spells.class));
        Day22.Boss boss = Day22.Boss.of(13, 8);

        assertThat(Day22.gamePartOne(player, boss)).isEqualTo(226);
    }

    @Test
    void inputLargeExample() {
        Day22.Player player = Day22.Player.of(10, 250, new EnumMap<>(Day22.Spells.class));
        Day22.Boss boss = Day22.Boss.of(14, 8);

        assertThat(Day22.gamePartOne(player, boss)).isEqualTo(641);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day22.gamePartOne(scanner)).isEqualTo(953);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day22.gamePartTwo(scanner)).isEqualTo(1289);
    }

}
