package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day22Test {

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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day22Test.class.getResourceAsStream("/2015/day/22/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day22.gamePartOne(scanner)).isEqualTo(953);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day22Test.class.getResourceAsStream("/2015/day/22/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day22.gamePartTwo(scanner)).isEqualTo(1289);
        }
    }

}
