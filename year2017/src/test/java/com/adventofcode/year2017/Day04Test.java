package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test {

    @Test
    void inputExample() {
        assertThat(Day04.validPassphrasePartOne("aa bb cc dd ee")).isTrue();
        assertThat(Day04.validPassphrasePartOne("aa bb cc dd aa")).isFalse();
        assertThat(Day04.validPassphrasePartOne("aa bb cc dd aaa")).isTrue();

        assertThat(Day04.validPassphrasePartTwo("abcde fghij")).isTrue();
        assertThat(Day04.validPassphrasePartTwo("abcde xyz ecdab")).isFalse();
        assertThat(Day04.validPassphrasePartTwo("a ab abc abd abf abj")).isTrue();
        assertThat(Day04.validPassphrasePartTwo("iiii oiii ooii oooi oooo")).isTrue();
        assertThat(Day04.validPassphrasePartTwo("oiii ioii iioi iiio")).isFalse();
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/4/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day04.validPassphrasePartOne(scanner)).isEqualTo(466);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/4/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day04.validPassphrasePartTwo(scanner)).isEqualTo(251);
        }
    }

}
