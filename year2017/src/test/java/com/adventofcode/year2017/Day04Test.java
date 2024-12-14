package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test extends AbstractTest {
    Day04Test() {
        super(2017, 4);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day04.validPassphrasePartOne(scanner)).isEqualTo(466);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day04.validPassphrasePartTwo(scanner)).isEqualTo(251);
    }

}
