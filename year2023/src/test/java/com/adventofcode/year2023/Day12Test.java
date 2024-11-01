package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day12Test {

    @Test
    void inputExample() {
        String input = """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1""";

        {
            Scanner scanner = new Scanner(input);
            int count = Day12.PartOne.countArrangements(scanner);
//
            Assertions.assertThat(count).isEqualTo(21);
        }

        {
            Scanner scanner = new Scanner(input);
            long count = Day12.PartTwo.countArrangements(scanner, 1);
            Assertions.assertThat(count).isEqualTo(21);
        }

        {
            Scanner scanner = new Scanner(input);
            long count = Day12.PartTwo.countArrangements(scanner, 5);
            Assertions.assertThat(count).isEqualTo(525152);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day12Test.class.getResourceAsStream("/2023/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int count = Day12.PartOne.countArrangements(scanner);

            Assertions.assertThat(count).isEqualTo(6871);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day12Test.class.getResourceAsStream("/2023/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long count = Day12.PartTwo.countArrangements(scanner, 5);

            Assertions.assertThat(count).isEqualTo(2043098029844L);
        }
    }

}
