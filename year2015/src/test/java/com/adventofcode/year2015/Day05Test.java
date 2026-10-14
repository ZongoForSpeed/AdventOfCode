package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 5)
class Day05Test {

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

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day05.niceStrings(scanner, Day05::niceStringPartOne)).isEqualTo(238);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day05.niceStrings(scanner, Day05::niceStringPartTwo)).isEqualTo(69);
    }
}
