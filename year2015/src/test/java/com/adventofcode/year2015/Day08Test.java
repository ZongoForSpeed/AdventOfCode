package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test extends AbstractTest {

    Day08Test() {
        super(2015, 8);
    }

    @Test
    void inputExample() {
        assertThat(Day08.decode("\"\"")).isZero();
        assertThat(Day08.decode("\"abc\"")).isEqualTo(3);
        assertThat(Day08.decode("\"aaa\\\"aaa\"")).isEqualTo(7);
        assertThat(Day08.decode("\"\\x27\"")).isEqualTo(1);
        assertThat(Day08.memoryDecode("\"\"")).isEqualTo(2);
        assertThat(Day08.memoryDecode("\"abc\"")).isEqualTo(2);
        assertThat(Day08.memoryDecode("\"aaa\\\"aaa\"")).isEqualTo(3);
        assertThat(Day08.memoryDecode("\"\\x27\"")).isEqualTo(5);
        assertThat(Day08.encode("\"\"")).isEqualTo(6);
        assertThat(Day08.encode("\"abc\"")).isEqualTo(9);
        assertThat(Day08.encode("\"aaa\\\"aaa\"")).isEqualTo(16);
        assertThat(Day08.encode("\"\\x27\"")).isEqualTo(11);

        String input = """
                ""
                "abc"
                "aaa\\"aaa"
                "\\x27"
                """;

        assertThat(Day08.memoryDecode(new Scanner(input))).isEqualTo(12);
        assertThat(Day08.memoryEncode(new Scanner(input))).isEqualTo(19);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day08.memoryDecode(scanner)).isEqualTo(1371);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day08.memoryEncode(scanner)).isEqualTo(2117);
    }
}
