package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 4)
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
    void partOne(Scanner scanner) {
        assertThat(Day04.validPassphrasePartOne(scanner)).isEqualTo(466);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day04.validPassphrasePartTwo(scanner)).isEqualTo(251);
    }

}
