package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day06Test extends AbstractTest {
    Day06Test() {
        super(2022, 6);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        Assertions.assertThat(Day06.findMarker(scanner.nextLine(), 4)).isEqualTo(1566);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Assertions.assertThat(Day06.findMarker(scanner.nextLine(), 14)).isEqualTo(2265);
    }

}
