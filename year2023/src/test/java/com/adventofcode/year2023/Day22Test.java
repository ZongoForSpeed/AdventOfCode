package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day22Test extends AbstractTest {

    Day22Test() {
        super(2023, 22);
    }

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
            var count = Day22.PartOne.countSupports(scanner);
            Assertions.assertThat(count).isEqualTo(5);
        }

        {
            var scanner = new Scanner(input);
            var count = Day22.PartTwo.countFalling(scanner);
            Assertions.assertThat(count).isEqualTo(7);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var count = Day22.PartOne.countSupports(scanner);
        Assertions.assertThat(count).isEqualTo(403);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var count = Day22.PartTwo.countFalling(scanner);
        Assertions.assertThat(count).isEqualTo(70189);
    }
}
