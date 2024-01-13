package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test {

    @Test
    void inputExample() {
        String input = """
                abcdef
                bababc
                abbcde
                abcccd
                aabcdd
                abcdee
                ababab""";

        Scanner scanner = new Scanner(input);
        assertThat(Day02.checksum(scanner)).isEqualTo(12);

        scanner = new Scanner("""
                abcde
                fghij
                klmno
                pqrst
                fguij
                axcye
                wvxyz""");

        assertThat(Day02.inventoryManagementSystem(scanner)).hasValue("fgij");
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2018/day/2/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day02.checksum(scanner)).isEqualTo(5166);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2018/day/2/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day02.inventoryManagementSystem(scanner)).hasValue("cypueihajytordkgzxfqplbwn");
        }
    }

}
