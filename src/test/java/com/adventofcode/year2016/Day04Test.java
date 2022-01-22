package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test {

    @Test
    void inputExample() {
        String input = """
                aaaaa-bbb-z-y-x-123[abxyz]
                a-b-c-d-e-f-g-h-987[abcde]
                not-a-real-room-404[oarel]
                totally-real-room-200[decoy]""";

        Scanner scanner = new Scanner(input);

        assertThat(Day04.checkRoomName(scanner)).isEqualTo(1514);

        assertThat(Day04.decryptRoomName("qzmt-zixmtkozy-ivhz", 343)).isEqualTo("very encrypted name");

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day04Test.class.getResourceAsStream("/2016/day/4/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day04.checkRoomName(scanner)).isEqualTo(245102);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day04Test.class.getResourceAsStream("/2016/day/4/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is));) {
            assertThat(Day04.decryptRoomName(scanner)).isEqualTo(324);
        }
    }

}
