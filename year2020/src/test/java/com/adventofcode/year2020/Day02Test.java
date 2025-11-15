package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test extends AbstractTest {
    Day02Test() {
        super(2020, 2);
    }

    @Test
    void passwordPolicy() {
        assertThat(Day02.passwordPolicy1("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy1("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy1("2-9 c: ccccccccc")).isTrue();

        assertThat(Day02.passwordPolicy2("1-3 a: abcde")).isTrue();
        assertThat(Day02.passwordPolicy2("1-3 b: cdefg")).isFalse();
        assertThat(Day02.passwordPolicy2("2-9 c: ccccccccc")).isFalse();
    }

    @Override
    public void partOne(Scanner scanner) {
        long count = 0;
        while (scanner.hasNextLine()) {
            if (Day02.passwordPolicy1(scanner.nextLine())) {
                count++;
            }
        }

        assertThat(count).isEqualTo(655);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long count = 0;
        while (scanner.hasNextLine()) {
            if (Day02.passwordPolicy2(scanner.nextLine())) {
                count++;
            }
        }

        assertThat(count).isEqualTo(673);
    }
}
