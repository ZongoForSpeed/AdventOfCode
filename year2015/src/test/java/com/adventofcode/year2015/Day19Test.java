package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 19)
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
    void partOne(Scanner scanner) {
        assertThat(Day19.findReplacements(scanner)).hasSize(535);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day19.findMedicine(scanner)).isEqualTo(212);
    }
}
