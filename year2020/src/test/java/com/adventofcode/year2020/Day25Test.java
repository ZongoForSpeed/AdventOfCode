package com.adventofcode.year2020;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 25)
class Day25Test {
    @Test
    void comboBreaker() {
        long cardEncryptionKey = Day25.comboBreaker(5764801, 17807724);
        assertThat(cardEncryptionKey).isEqualTo(14897079);
    }

    @Test
    void partOne(Scanner scanner) {
        long cardPublicKey = Long.parseLong(scanner.nextLine());
        long doorPublicKey = Long.parseLong(scanner.nextLine());
        long encryptionKey = Day25.comboBreaker(cardPublicKey, doorPublicKey);
        assertThat(encryptionKey).isEqualTo(16933668);
    }

    @Test
    void partTwo(Scanner scanner) {
        // No-Op
    }
}
