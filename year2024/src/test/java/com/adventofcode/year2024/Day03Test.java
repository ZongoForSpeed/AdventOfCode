package com.adventofcode.year2024;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void inputExample() {

        {
            String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
            long results = Day03.partOne(input);
            assertThat(results).isEqualTo(161);
        }

        {
            String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
            long results = Day03.partTwo(input);
            assertThat(results).isEqualTo(48);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2024/day/03/input");) {
            String input = IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
            assertThat(Day03.partOne(input)).isEqualTo(179571322L);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day03Test.class.getResourceAsStream("/2024/day/03/input");) {
            String input = IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
            assertThat(Day03.partTwo(input)).isEqualTo(103811193L);
        }
    }

}
