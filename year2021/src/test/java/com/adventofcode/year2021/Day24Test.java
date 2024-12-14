package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test extends AbstractTest { Day24Test() { super(2021, 24); }

    @Override
    public void partOne(Scanner scanner) {
            assertThat(Day24.aluPartOne(scanner)).isEqualTo(39924989499969L);
    }

    @Override
    public void partTwo(Scanner scanner) {
            assertThat(Day24.aluPartTwo(scanner)).isEqualTo(16811412161117L);
    }

}
