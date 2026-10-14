package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test extends AbstractTest {
    Day25Test() {
        super(2020, 25);
    }

    @Test
    void comboBreaker() {
        long cardEncryptionKey = Day25.comboBreaker(5764801, 17807724);
        assertThat(cardEncryptionKey).isEqualTo(14897079);
    }

    @Override
    public void partOne(Scanner scanner) {
        long cardPublicKey = Long.parseLong(scanner.nextLine());
        long doorPublicKey = Long.parseLong(scanner.nextLine());
        long encryptionKey = Day25.comboBreaker(cardPublicKey, doorPublicKey);
        assertThat(encryptionKey).isEqualTo(16933668);
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }
}
