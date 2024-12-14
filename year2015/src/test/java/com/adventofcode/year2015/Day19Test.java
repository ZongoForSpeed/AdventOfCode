package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day19Test extends AbstractTest {
    Day19Test() {
        super(2015, 19);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day19.findReplacements(scanner)).hasSize(535);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day19.findMedicine(scanner)).isEqualTo(212);
    }
}
