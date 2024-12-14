package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;
import java.util.Scanner;

class Day07Test extends AbstractTest {
    Day07Test() {
        super(2022, 7);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        long sum = Day07.PartOne.findSum(scanner);
        Assertions.assertThat(sum).isEqualTo(1307902);
    }

    @Override
    public void partTwo(Scanner scanner) {
        OptionalLong min = Day07.PartTwo.findSmallestToDelete(scanner);
        Assertions.assertThat(min).hasValue(7068748);
    }

}
