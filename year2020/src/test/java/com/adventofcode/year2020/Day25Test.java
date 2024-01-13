package com.adventofcode.year2020;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    @Test
    void testComboBreaker() {
        long cardEncryptionKey = Day25.comboBreaker(5764801, 17807724);
        assertThat(cardEncryptionKey).isEqualTo(14897079);
    }

    @Test
    void inputComboBreaker() throws IOException {
        try (InputStream inputStream = Day25Test.class.getResourceAsStream("/2020/day/25/input"); Scanner scanner = new Scanner(inputStream)) {
            long cardPublicKey = Long.parseLong(scanner.nextLine());
            long doorPublicKey = Long.parseLong(scanner.nextLine());
            long encryptionKey = Day25.comboBreaker(cardPublicKey, doorPublicKey);
            assertThat(encryptionKey).isEqualTo(16933668);
        }

    }
}
