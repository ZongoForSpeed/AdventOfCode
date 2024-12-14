package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test extends AbstractTest {
    Day06Test() {
        super(2016, 6);
    }

    @Test
    void inputExample() {
        String input = """
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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.decodePasswordMostCommon(scanner)).isEqualTo("mlncjgdg");
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.decodePasswordLeastCommon(scanner)).isEqualTo("bipjaytb");
    }
}
