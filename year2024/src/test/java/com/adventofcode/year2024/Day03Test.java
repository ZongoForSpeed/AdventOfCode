package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test extends AbstractTest {

    protected Day03Test() {
        super(2024, 3);
    }

    @Test
    void inputExample() {

        try (Scanner scanner = new Scanner("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")){
            long results = Day03.partOne(scanner);
            assertThat(results).isEqualTo(161);
        }

        try (Scanner scanner = new Scanner("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")){
            long results = Day03.partTwo(scanner);
            assertThat(results).isEqualTo(48);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day03.partOne(scanner)).isEqualTo(179571322L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day03.partTwo(scanner)).isEqualTo(103811193L);
    }

}
