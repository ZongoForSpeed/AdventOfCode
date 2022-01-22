package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test {

    @Test
    void inputExample() {
        assertThat(Day07.abba("abba")).isTrue();
        assertThat(Day07.abba("aaaa")).isFalse();
        assertThat(Day07.abba("xyyx")).isTrue();

        assertThat(Day07.supportTLS("abba[mnop]qrst")).isTrue();
        assertThat(Day07.supportTLS("abcd[bddb]xyyx")).isFalse();
        assertThat(Day07.supportTLS("aaaa[qwer]tyui")).isFalse();
        assertThat(Day07.supportTLS("ioxxoj[asdfgh]zxcvbn")).isTrue();

        assertThat(Day07.supportSSL("aba[bab]xyz")).isTrue();
        assertThat(Day07.supportSSL("xyx[xyx]xyx")).isFalse();
        assertThat(Day07.supportSSL("aaa[kek]eke")).isTrue();
        assertThat(Day07.supportSSL("zazbz[bzb]cdb")).isTrue();
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2016/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.supportTLS(scanner)).isEqualTo(118);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day07Test.class.getResourceAsStream("/2016/day/7/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day07.supportSSL(scanner)).isEqualTo(260);
        }
    }
}
