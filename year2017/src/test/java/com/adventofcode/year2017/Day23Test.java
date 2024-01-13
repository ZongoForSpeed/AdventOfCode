package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);


    static long runTest(long b, long c, long o) {
        long mul = 0;
        long a = 0;
        long d;
        long e;
        long f;
        long g;
        long h = 0;
        do {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    g = d * e;
                    mul++;
                    g -= b;
                    if (g == 0) {
                        f = 0;
                    }
                    e++;
                    g = e;
                    g -= b;
                } while (g != 0);
                d -= -1;
                g = d;
                g -= b;
            } while (g != 0);
            if (f == 0) {
                h++;
            }
            g = b;
            g -= c;
            if (g == 0) {
                break;
            }
            b -= -17;
        } while (true);

        LOGGER.info("a = {}", a);
        LOGGER.info("b = {}", b);
        LOGGER.info("c = {}", c);
        LOGGER.info("d = {}", d);
        LOGGER.info("e = {}", e);
        LOGGER.info("f = {}", f);
        LOGGER.info("g = {}", g);
        LOGGER.info("h = {}", h);

        assertThat(mul).isEqualTo(o);

        return h;
    }

    @Test
    void testAssembly1() {
        assertThat(runTest(57, 57, 3025)).isEqualTo(1);
        assertThat(runTest(57, 57 + 10 * 17, 247390)).isEqualTo(9);
        assertThat(Day23.runProgramFast(57, 57 + 10 * 17)).isEqualTo(10);
    }

    @Test
    void inputExample() {
        String input = """
                set b 57
                set c b
                jnz a 2
                jnz 1 5
                mul b 100
                sub b -100000
                set c b
                sub c -17000""";

        Scanner scanner = new Scanner(input);
        List<String[]> commands = Day23.readCommands(scanner);

        Map<String, Long> registers = new HashMap<>(Map.of(
                "a", 1L,
                "b", 0L,
                "c", 0L,
                "d", 0L,
                "e", 0L,
                "f", 0L,
                "g", 0L,
                "h", 0L
        ));
        Day23.runProgram(commands, registers);

        LOGGER.info("registers: {}", registers);

        assertThat(registers)
                .containsEntry("b", 105700L)
                .containsEntry("c", 122700L);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/23/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            List<String[]> commands = Day23.readCommands(scanner);

            Map<String, Long> registers = new HashMap<>(Map.of(
                    "a", 0L,
                    "b", 0L,
                    "c", 0L,
                    "d", 0L,
                    "e", 0L,
                    "f", 0L,
                    "g", 0L,
                    "h", 0L
            ));

            assertThat(Day23.runProgram(commands, registers)).isEqualTo(3025);
        }
    }

    @Test
    void inputPartTwo() {
        assertThat(Day23.runProgramFast(105700, 122700)).isEqualTo(915L);
    }

}
