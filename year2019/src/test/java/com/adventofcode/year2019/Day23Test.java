package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.SAME_THREAD)
@AdventOfCode(year = 2019, day = 23)
class Day23Test {

    @Test
    void partOne(Scanner scanner) throws InterruptedException {
        String program = scanner.nextLine();
        assertThat(Day23.runNetworkInterfaceController(program)).isEqualTo(20367);
    }

    @Test
    void partTwo(Scanner scanner) throws InterruptedException {
        String program = scanner.nextLine();
        assertThat(Day23.runNotAlwaysTransmitting(program)).isEqualTo(15080);
    }
}
