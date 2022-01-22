package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day21Test {

    @Test
    void inputExample() {
        Day21.Player player = Day21.Player.of("player", 8, 5, 5);
        Day21.Player boss = Day21.Player.of("boss", 12, 7, 2);

        assertThat(Day21.fight(player, boss)).isTrue();
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2015/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day21.runFightsPartOne(scanner)).hasValue(111);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day21Test.class.getResourceAsStream("/2015/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day21.runFightsPartTwo(scanner)).hasValue(188);
        }
    }

}
