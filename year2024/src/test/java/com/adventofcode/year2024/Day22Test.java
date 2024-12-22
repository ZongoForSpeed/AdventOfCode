package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test extends AbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    Day22Test() {
        super(2024, 22);
    }

    @Test
    void inputExample1() {
        long secretNumber = 123;
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(15887950);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(16495136);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(527345);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(704524);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(1553684);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(12683156);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(11100544);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(12249484);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(7753432);
        secretNumber = Day22.nextSecretNumber(secretNumber);
        assertThat(secretNumber).isEqualTo(5908254);
    }

    @Test
    void inputExample2() {
        String input = """
                1
                10
                100
                2024""";

        try (Scanner scanner = new Scanner(input)) {
            long sum = Day22.partOne(scanner);
            assertThat(sum).isEqualTo(37327623);
        }
    }

    @Test
    void inputExample3() {
        long secretNumber = 123;

        Map<Day22.PriceChange, Integer> changes = Day22.findPriceChanges(secretNumber, 10);

        LOGGER.info("changes: {}", changes);
        assertThat(changes).containsEntry(new Day22.PriceChange(-1, -1, 0, 2), 6);
    }

    @Test
    void inputExample4() {
        String input = """
                1
                2
                3
                2024""";

        try (Scanner scanner = new Scanner(input)) {
            int bananas = Day22.partTwo(scanner);
            assertThat(bananas).isEqualTo(23);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        long sum = Day22.partOne(scanner);
        assertThat(sum).isEqualTo(19458130434L);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        int bananas = Day22.partTwo(scanner);
        assertThat(bananas).isEqualTo(2130);
    }

}
