package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void testExample() {
        String input = """
                root: pppw + sjmn
                dbpl: 5
                cczh: sllz + lgvd
                zczc: 2
                ptdq: humn - dvpt
                dvpt: 3
                lfqf: 4
                humn: 5
                ljgn: 2
                sjmn: drzm * dbpl
                sllz: 4
                pppw: cczh / lfqf
                lgvd: ljgn * ptdq
                drzm: hmdt - zczc
                hmdt: 32""";

        {
            Scanner scanner = new Scanner(input);
            long shout = Day21.partOne(scanner);
            assertThat(shout).isEqualTo(152);
        }

        {
            Scanner scanner = new Scanner(input);
            long shout = Day21.partTwo(scanner);
            assertThat(shout).isEqualTo(301);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day21.partOne(scanner)).isEqualTo(72664227897438L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            assertThat(Day21.partTwo(scanner)).isEqualTo(3916491093817L);
        }
    }

}
