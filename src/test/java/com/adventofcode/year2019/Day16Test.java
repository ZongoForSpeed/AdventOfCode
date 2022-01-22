package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {


    @Test
    void testSimple() {
        assertThat(Day16.flawedFrequencyTransmission("12345678", 1)).isEqualTo("48226158");
        assertThat(Day16.flawedFrequencyTransmission("12345678", 2)).isEqualTo("34040438");
        assertThat(Day16.flawedFrequencyTransmission("12345678", 3)).isEqualTo("03415518");
        assertThat(Day16.flawedFrequencyTransmission("12345678", 4)).isEqualTo("01029498");
    }

    @Test
    void testLargerExample() {
        assertThat(Day16.flawedFrequencyTransmission("80871224585914546619083218645595", 100)).startsWith("24176176");
        assertThat(Day16.flawedFrequencyTransmission("19617804207202209144916044189917", 100)).startsWith("73745418");
        assertThat(Day16.flawedFrequencyTransmission("69317163492948606335995924319873", 100)).startsWith("52432133");
    }

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/16/input");
        assertThat(Day16.flawedFrequencyTransmission(line, 100)).startsWith("32002835");
    }

    @Test
    void testPattern() {
        assertThat(Day16.pattern(1)).startsWith(1, 0, -1, 0, 1, 0, -1, 0, 1, 0, -1, 0, 1, 0, -1, 0, 1, 0, -1, 0, 1);
        assertThat(Day16.pattern(2)).startsWith(0, 1, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0);
        assertThat(Day16.pattern(3)).startsWith(0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 1, 1, 1, 0, 0, 0, -1);
        assertThat(Day16.pattern(4)).startsWith(0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1);
    }

    @Test
    void testDecode() {
        assertThat(Day16.decode("03036732577212944063491565474664")).isEqualTo("84462026");
        assertThat(Day16.decode("02935109699940807407585447034323")).isEqualTo("78725270");
        assertThat(Day16.decode("03081770884921959731165446850517")).isEqualTo("53553731");
    }

    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/16/input");
        assertThat(Day16.decode(line)).isEqualTo("69732268");
    }

}
