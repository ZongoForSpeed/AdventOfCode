package com.adventofcode.year2022;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Scanner;

class Day07Test {

    @Test
    void inputExample() {
        String input = """
                $ cd /
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                $ ls
                dir e
                29116 f
                2557 g
                62596 h.lst
                $ cd e
                $ ls
                584 i
                $ cd ..
                $ cd ..
                $ cd d
                $ ls
                4060174 j
                8033020 d.log
                5626152 d.ext
                7214296 k""";

        {
            Scanner scanner = new Scanner(input);
            long sum = Day07.PartOne.findSum(scanner);
            Assertions.assertThat(sum).isEqualTo(95437);
        }

        {
            Scanner scanner = new Scanner(input);
            OptionalLong min = Day07.PartTwo.findSmallestToDelete(scanner);
            Assertions.assertThat(min).hasValue(24933642);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2022/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            long sum = Day07.PartOne.findSum(scanner);
            Assertions.assertThat(sum).isEqualTo(1307902);
        }
    }

    @Test
    void inputPartTwp() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2022/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            OptionalLong min = Day07.PartTwo.findSmallestToDelete(scanner);
            Assertions.assertThat(min).hasValue(7068748);
        }
    }

}
