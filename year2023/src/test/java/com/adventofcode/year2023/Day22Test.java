package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day22Test {

    @Test
    void inputExample() {
        String input = """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9""";

        {
            Scanner scanner = new Scanner(input);
            int count = Day22.PartOne.countSupports(scanner);
            Assertions.assertThat(count).isEqualTo(5);
        }

        {
            Scanner scanner = new Scanner(input);
            long count = Day22.PartTwo.countFalling(scanner);
            Assertions.assertThat(count).isEqualTo(7);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day22Test.class.getResourceAsStream("/2023/day/22/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int count = Day22.PartOne.countSupports(scanner);
            Assertions.assertThat(count).isEqualTo(403);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day22Test.class.getResourceAsStream("/2023/day/22/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long count = Day22.PartTwo.countFalling(scanner);
            Assertions.assertThat(count).isEqualTo(70189);
        }
    }
}
