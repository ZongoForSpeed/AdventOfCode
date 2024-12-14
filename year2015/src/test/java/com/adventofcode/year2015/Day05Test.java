package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test extends AbstractTest {

    Day05Test() {
        super(2015, 5);
    }

    @Test
    void inputExample() {
        assertThat(Day05.niceStringPartOne("ugknbfddgicrmopn")).isTrue();
        assertThat(Day05.niceStringPartOne("aaa")).isTrue();
        assertThat(Day05.niceStringPartOne("jchzalrnumimnmhp")).isFalse();
        assertThat(Day05.niceStringPartOne("haegwjzuvuyypxyu")).isFalse();
        assertThat(Day05.niceStringPartOne("dvszwmarrgswjxmb")).isFalse();


        assertThat(Day05.niceStringPartTwo("qjhvhtzxzqqjkmpb")).isTrue();
        assertThat(Day05.niceStringPartTwo("xxyxx")).isTrue();
        assertThat(Day05.niceStringPartTwo("uurcxstgmygtbstg")).isFalse();
        assertThat(Day05.niceStringPartTwo("ieodomkazucvgmuy")).isFalse();
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day05.niceStrings(scanner, Day05::niceStringPartOne)).isEqualTo(238);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day05.niceStrings(scanner, Day05::niceStringPartTwo)).isEqualTo(69);
    }
}
