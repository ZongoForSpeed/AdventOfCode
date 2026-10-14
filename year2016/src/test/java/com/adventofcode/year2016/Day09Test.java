package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 9)
class Day09Test {
    @Test
    void inputExample() throws Exception {
        assertThat(Day09.decompressV1("ADVENT")).isEqualTo("ADVENT");
        assertThat(Day09.decompressV1("A(1x5)BC")).isEqualTo("ABBBBBC");
        assertThat(Day09.decompressV1("(3x3)XYZ")).isEqualTo("XYZXYZXYZ");
        assertThat(Day09.decompressV1("A(2x2)BCD(2x2)EFG")).isEqualTo("ABCBCDEFEFG");
        assertThat(Day09.decompressV1("(6x1)(1x3)A")).isEqualTo("(1x3)A");
        assertThat(Day09.decompressV1("X(8x2)(3x3)ABCY")).isEqualTo("X(3x3)ABC(3x3)ABCY");

        assertThat(Day09.decompressV2("(3x3)XYZ")).isEqualTo(9);
        assertThat(Day09.decompressV2("X(8x2)(3x3)ABCY")).isEqualTo(20);
        assertThat(Day09.decompressV2("(27x12)(20x12)(13x14)(7x10)(1x12)A")).isEqualTo(241920);
        assertThat(Day09.decompressV2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")).isEqualTo(445);
    }

    @Test
    void partOne(Scanner scanner) throws IOException {
        assertThat(Day09.decompressV1(scanner.nextLine())).hasSize(150914);
    }

    @Test
    void partTwo(Scanner scanner) throws IOException {
        assertThat(Day09.decompressV2(scanner.nextLine())).isEqualTo(11052855125L);
    }
}
