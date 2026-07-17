package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day23Test extends AbstractTest {
    Day23Test() {
        super(2015, 23);
    }

    @Test
    void inputExample() {
        var input = """
                inc a
                jio a, +2
                tpl a
                inc a""";

        var scanner = new Scanner(input);
        var registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('a', 2L);

    }

    @Override
    public void partOne(Scanner scanner) {
        var registers = Day23.runInstructions(scanner, 0);
        assertThat(registers).containsEntry('b', 255L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var registers = Day23.runInstructions(scanner, 1);
        assertThat(registers).containsEntry('b', 334L);
    }
}
