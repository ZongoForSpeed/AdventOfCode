package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 6)
class Day06Test {
    @Test
    void inputExample() {
        var input = """
                eedadn
                drvtee
                eandsr
                raavrd
                atevrs
                tsrnev
                sdttsa
                rasrtv
                nssdts
                ntnada
                svetve
                tesnvt
                vntsnd
                vrdear
                dvrsen
                enarar""";

        assertThat(Day06.decodePasswordMostCommon(new Scanner(input))).isEqualTo("easter");
        assertThat(Day06.decodePasswordLeastCommon(new Scanner(input))).isEqualTo("advent");
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day06.decodePasswordMostCommon(scanner)).isEqualTo("mlncjgdg");
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day06.decodePasswordLeastCommon(scanner)).isEqualTo("bipjaytb");
    }
}
