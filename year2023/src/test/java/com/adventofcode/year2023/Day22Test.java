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

    @Override
    public void partOne(Scanner scanner) {
        int count = Day22.PartOne.countSupports(scanner);
        Assertions.assertThat(count).isEqualTo(403);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long count = Day22.PartTwo.countFalling(scanner);
        Assertions.assertThat(count).isEqualTo(70189);
    }
}
