package com.adventofcode.year2020;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 2)
class Day02Test {
    @Test
    void passwordPolicy() {
        assertThat(Day02.passwordPolicy1("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy1("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy1("2-9 c: ccccccccc")).isTrue();

        assertThat(Day02.passwordPolicy2("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy2("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy2("2-9 c: ccccccccc")).isFalse();
    }

    @Test
    void partOne(Scanner scanner) {
        long count = 0L;
        while (scanner.hasNextLine()) {
            if (Day02.passwordPolicy1(scanner.nextLine())) {
                count++;
            }
        }

        assertThat(count).isEqualTo(655);
    }

    @Test
    void partTwo(Scanner scanner) {
        long count = 0L;
        while (scanner.hasNextLine()) {
            if (Day02.passwordPolicy2(scanner.nextLine())) {
                count++;
            }
        }

        assertThat(count).isEqualTo(673);
    }
}
