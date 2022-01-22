package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    @Test
    void inputExample() {

        assertThat(Day16.version("D2FE28")).isEqualTo(6);
        assertThat(Day16.version("38006F45291200")).isEqualTo(9);
        assertThat(Day16.version("EE00D40C823060")).isEqualTo(14);
        assertThat(Day16.version("8A004A801A8002F478")).isEqualTo(16);
        assertThat(Day16.version("620080001611562C8802118E34")).isEqualTo(12);
        assertThat(Day16.version("C0015000016115A2E0802F182340")).isEqualTo(23);
        assertThat(Day16.version("A0016C880162017C3686B18A3D4780")).isEqualTo(31);

        assertThat(Day16.evaluate("C200B40A82")).isEqualTo(3);
        assertThat(Day16.evaluate("04005AC33890")).isEqualTo(54);
        assertThat(Day16.evaluate("880086C3E88112")).isEqualTo(7);
        assertThat(Day16.evaluate("CE00C43D881120")).isEqualTo(9);
        assertThat(Day16.evaluate("D8005AC2A8F0")).isEqualTo(1);
        assertThat(Day16.evaluate("F600BC2D8F")).isZero();
        assertThat(Day16.evaluate("9C005AC2F8F0")).isZero();
        assertThat(Day16.evaluate("9C0141080250320F1802104A08")).isEqualTo(1);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2021/day/16/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day16.version(scanner.nextLine())).isEqualTo(879);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2021/day/16/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day16.evaluate(scanner.nextLine())).isEqualTo(539051801941L);
        }
    }

}
