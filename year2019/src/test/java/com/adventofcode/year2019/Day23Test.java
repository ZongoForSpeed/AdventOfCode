package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test extends AbstractTest {
    Day23Test() {
        super(2019, 23);
    }

    @Override
    public void partOne(Scanner scanner) throws InterruptedException {
        String program = scanner.nextLine();
        assertThat(Day23.runNetworkInterfaceController(program)).isEqualTo(20367);
    }

    @Override
    public void partTwo(Scanner scanner) throws InterruptedException {
        String program = scanner.nextLine();
        assertThat(Day23.runNotAlwaysTransmitting(program)).isEqualTo(15080);
    }
}
