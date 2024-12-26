package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test extends AbstractTest {

    protected Day25Test() {
        super(2022, 25);
    }

    @Test
    void inputExample1() {
        //   Decimal          SNAFU
        //        1              1
        //        2              2
        //        3             1=
        //        4             1-
        //        5             10
        //        6             11
        //        7             12
        //        8             2=
        //        9             2-
        //       10             20
        //       15            1=0
        //       20            1-0
        //     2022         1=11-2
        //    12345        1-0---0
        //314159265  1121-1110-1=0
        assertThat(Day25.decimalToSNAFU(1)).isEqualTo("1");
        assertThat(Day25.decimalToSNAFU(2)).isEqualTo("2");
        assertThat(Day25.decimalToSNAFU(3)).isEqualTo("1=");
        assertThat(Day25.decimalToSNAFU(4)).isEqualTo("1-");
        assertThat(Day25.decimalToSNAFU(5)).isEqualTo("10");
        assertThat(Day25.decimalToSNAFU(6)).isEqualTo("11");
        assertThat(Day25.decimalToSNAFU(7)).isEqualTo("12");
        assertThat(Day25.decimalToSNAFU(8)).isEqualTo("2=");
        assertThat(Day25.decimalToSNAFU(9)).isEqualTo("2-");
        assertThat(Day25.decimalToSNAFU(10)).isEqualTo("20");
        assertThat(Day25.decimalToSNAFU(15)).isEqualTo("1=0");
        assertThat(Day25.decimalToSNAFU(20)).isEqualTo("1-0");
        assertThat(Day25.decimalToSNAFU(2022)).isEqualTo("1=11-2");
        assertThat(Day25.decimalToSNAFU(12345)).isEqualTo("1-0---0");
        assertThat(Day25.decimalToSNAFU(314159265)).isEqualTo("1121-1110-1=0");

        assertThat(Day25.snafuToDecimal("1")).isEqualTo(1);
        assertThat(Day25.snafuToDecimal("2")).isEqualTo(2);
        assertThat(Day25.snafuToDecimal("1=")).isEqualTo(3);
        assertThat(Day25.snafuToDecimal("1-")).isEqualTo(4);
        assertThat(Day25.snafuToDecimal("10")).isEqualTo(5);
        assertThat(Day25.snafuToDecimal("11")).isEqualTo(6);
        assertThat(Day25.snafuToDecimal("12")).isEqualTo(7);
        assertThat(Day25.snafuToDecimal("2=")).isEqualTo(8);
        assertThat(Day25.snafuToDecimal("2-")).isEqualTo(9);
        assertThat(Day25.snafuToDecimal("20")).isEqualTo(10);
        assertThat(Day25.snafuToDecimal("1=0")).isEqualTo(15);
        assertThat(Day25.snafuToDecimal("1-0")).isEqualTo(20);
        assertThat(Day25.snafuToDecimal("1=11-2")).isEqualTo(2022);
        assertThat(Day25.snafuToDecimal("1-0---0")).isEqualTo(12345);
        assertThat(Day25.snafuToDecimal("1121-1110-1=0")).isEqualTo(314159265);
    }


    @Test
    void inputExample2() {

        String input = """
                1=-0-2
                12111
                2=0=
                21
                2=01
                111
                20012
                112
                1=-1=
                1-12
                12
                1=
                122""";

        try (Scanner scanner = new Scanner(input)) {
            Day25.Result result = Day25.partOne(scanner);

            assertThat(result.decimal()).isEqualTo(4890);
            assertThat(result.snafu()).isEqualTo("2=-1=0");
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        Day25.Result result = Day25.partOne(scanner);

        assertThat(result.decimal()).isEqualTo(31561222762257L);
        assertThat(result.snafu()).isEqualTo("2=12-100--1012-0=012");
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {

    }

}
