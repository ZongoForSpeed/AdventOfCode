package com.adventofcode.year2022;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

class Day06Test {

    @Test
    void inputExample1() {
        Assertions.assertThat(Day06.findMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4)).isEqualTo(7);
        Assertions.assertThat(Day06.findMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14)).isEqualTo(19);
    }
    @Test
    void inputExample2() {
        Assertions.assertThat(Day06.findMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 4)).isEqualTo(5);
        Assertions.assertThat(Day06.findMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14)).isEqualTo(23);
    }
    @Test
    void inputExample3() {
        Assertions.assertThat(Day06.findMarker("nppdvjthqldpwncqszvftbrmjlhg", 4)).isEqualTo(6);
        Assertions.assertThat(Day06.findMarker("nppdvjthqldpwncqszvftbrmjlhg", 14)).isEqualTo(23);
    }
    @Test
    void inputExample4() {
        Assertions.assertThat(Day06.findMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4)).isEqualTo(10);
        Assertions.assertThat(Day06.findMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14)).isEqualTo(29);
    }

    @Test
    void inputExample5() {
        Assertions.assertThat(Day06.findMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4)).isEqualTo(11);
        Assertions.assertThat(Day06.findMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14)).isEqualTo(26);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day06Test.class.getResourceAsStream("/2022/day/06/input")) {
            String line = IOUtils.toString(Objects.requireNonNull(inputStream));
            Assertions.assertThat(Day06.findMarker(line, 4)).isEqualTo(1566);
            Assertions.assertThat(Day06.findMarker(line, 14)).isEqualTo(2265);
        }
    }

}
