package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void testPasswordPolicy() {
        assertThat(Day02.passwordPolicy1("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy1("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy1("2-9 c: ccccccccc")).isTrue();

        assertThat(Day02.passwordPolicy2("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy2("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy2("2-9 c: ccccccccc")).isFalse();
    }

    @Test
    void inputPasswordPolicy() throws IOException {
        List<String> lines = FileUtils.readLines("/2020/day/2/input");

        assertThat(lines.stream().filter(Day02::passwordPolicy1).count())
                .isEqualTo(655);

        assertThat(lines.stream().filter(Day02::passwordPolicy2).count())
                .isEqualTo(673);
    }

}
