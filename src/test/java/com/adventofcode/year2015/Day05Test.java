package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


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
    void inputPartOne() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2015/day/5/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.niceStrings(scanner, Day05::niceStringPartOne)).isEqualTo(238);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2015/day/5/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.niceStrings(scanner, Day05::niceStringPartTwo)).isEqualTo(69);
        }
    }
}
