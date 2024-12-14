package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day07Test extends AbstractTest {
    Day07Test() {
        super(2016, 7);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day07.supportTLS(scanner)).isEqualTo(118);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day07.supportSSL(scanner)).isEqualTo(260);
    }
}
