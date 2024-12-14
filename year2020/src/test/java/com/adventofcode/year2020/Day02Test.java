package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test extends AbstractTest {
    Day02Test() {
        super(2020, 2);
    }

    @Test
    void testPasswordPolicy() {
        assertThat(Day02.passwordPolicy1("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy1("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy1("2-9 c: ccccccccc")).isTrue();

        assertThat(Day02.passwordPolicy2("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy2("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy2("2-9 c: ccccccccc")).isFalse();
    }

    @Override
    public void partOne(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines.stream().filter(Day02::passwordPolicy1).count())
                .isEqualTo(655);
    }

    @Override
    public void partTwo(Scanner scanner) {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines.stream().filter(Day02::passwordPolicy2).count())
                .isEqualTo(673);
    }
}
