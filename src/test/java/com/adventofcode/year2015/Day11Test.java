package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day11Test {

    @Test
    void inputExample() {
        assertThat(Day11.validatePassword("hijklmmn")).isFalse();
        assertThat(Day11.validatePassword("abbceffg")).isFalse();
        assertThat(Day11.validatePassword("abbcegjk")).isFalse();
        assertThat(Day11.validatePassword("abcdffaa")).isTrue();
        assertThat(Day11.validatePassword("ghjaabcc")).isTrue();
        assertThat(Day11.decode(Day11.encode("abcdefgh"))).isEqualTo("abcdefgh");
        assertThat(Day11.nextPassword("abcdefgh")).isEqualTo("abcdffaa");
        assertThat(Day11.nextPassword("ghijklmn")).isEqualTo("ghjaabcc");
    }


    @Test
    void inputPartOne() throws IOException {
        assertThat(Day11.nextPassword("cqjxjnds")).isEqualTo("cqjxxyzz");
    }

    @Test
    void inputPartTwo() throws IOException {
        assertThat(Day11.nextPassword("cqjxxyzz")).isEqualTo("cqkaabcc");
    }
}
