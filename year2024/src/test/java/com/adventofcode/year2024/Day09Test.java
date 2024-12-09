package com.adventofcode.year2024;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    @Test
    void inputExample1() {
        String input = "12345";

        assertThat(Day09.partOne(input)).isEqualTo(60);
    }

    @Test
    void inputExample2() {
        String input = "2333133121414131402";

        assertThat(Day09.partOne(input)).isEqualTo(1928);

        assertThat(Day09.partTwo(input)).isEqualTo(2858);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2024/day/09/input")) {
            String input = IOUtils.toString(Objects.requireNonNull(is), Charset.defaultCharset());
            assertThat(Day09.partOne(input)).isEqualTo(6360094256423L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2024/day/09/input")) {
            String input = IOUtils.toString(Objects.requireNonNull(is), Charset.defaultCharset());
            assertThat(Day09.partTwo(input)).isEqualTo(6379677752410L);
        }
    }

}
