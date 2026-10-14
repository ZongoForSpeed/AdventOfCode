package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 4)
class Day04Test {
    @Test
    void inputExample() {
        var input = """
                aaaaa-bbb-z-y-x-123[abxyz]
                a-b-c-d-e-f-g-h-987[abcde]
                not-a-real-room-404[oarel]
                totally-real-room-200[decoy]""";

        var scanner = new Scanner(input);

        assertThat(Day04.checkRoomName(scanner)).isEqualTo(1514);

        assertThat(Day04.decryptRoomName("qzmt-zixmtkozy-ivhz", 343)).isEqualTo("very encrypted name");

    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day04.checkRoomName(scanner)).isEqualTo(245102);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day04.decryptRoomName(scanner)).isEqualTo(324);
    }

}
