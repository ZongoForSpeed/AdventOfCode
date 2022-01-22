package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day22Test {


    @Test
    void inputExample() throws InterruptedException {
        String input = """
                ..#
                #..
                ...""";

        assertThat(Day22.sporificaVirusPartOne(new Scanner(input), 70)).isEqualTo(41);
        assertThat(Day22.sporificaVirusPartOne(new Scanner(input), 10000)).isEqualTo(5587);

        assertThat(Day22.sporificaVirusPartTwo(new Scanner(input), 100)).isEqualTo(26);
        assertThat(Day22.sporificaVirusPartTwo(new Scanner(input), 10_000_000)).isEqualTo(2511944);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/22/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day22.sporificaVirusPartOne(scanner, 10000)).isEqualTo(5575);
        }
    }

    @Test
    void inputPartTwo() throws IOException, InterruptedException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/22/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day22.sporificaVirusPartTwo(scanner, 10_000_000)).isEqualTo(2511991);
        }
    }

}
