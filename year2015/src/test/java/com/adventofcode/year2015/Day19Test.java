package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day19Test {

    @Test
    void inputExample() {
        assertThat(Day19.findReplacements(new Scanner("""
                H => HO
                H => OH
                O => HH
                                
                HOH"""))).hasSize(4);

        assertThat(Day19.findReplacements(new Scanner("""
                H => HO
                H => OH
                O => HH
                                
                HOHOHO"""))).hasSize(7);

        assertThat(Day19.findMedicine(new Scanner("""
                e => H
                e => O
                H => HO
                H => OH
                O => HH
                                
                HOH"""))).isEqualTo(3);

        assertThat(Day19.findMedicine(new Scanner("""
                e => H
                e => O
                H => HO
                H => OH
                O => HH
                                
                HOHOHO"""))).isEqualTo(6);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2015/day/19/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.findReplacements(scanner)).hasSize(535);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2015/day/19/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.findMedicine(scanner)).isEqualTo(212);
        }
    }
}
