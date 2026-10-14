package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 22)
class Day22Test {

    @Test
    void inputExample() {
        var input = """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9""";

        {
            var scanner = new Scanner(input);
            int count = Day22.PartOne.countSupports(scanner);
            Assertions.assertThat(count).isEqualTo(5);
        }

        {
            var scanner = new Scanner(input);
            long count = Day22.PartTwo.countFalling(scanner);
            Assertions.assertThat(count).isEqualTo(7);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int count = Day22.PartOne.countSupports(scanner);
        Assertions.assertThat(count).isEqualTo(403);
    }

    @Test
    void partTwo(Scanner scanner) {
        long count = Day22.PartTwo.countFalling(scanner);
        Assertions.assertThat(count).isEqualTo(70189);
    }
}
