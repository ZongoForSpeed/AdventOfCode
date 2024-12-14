package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test extends AbstractTest {
    Day15Test() {
        super(2021, 15);
    }

    @Test
    void inputExample() {
        String input = """
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581""";

        assertThat(Day15.chiton(new Scanner(input))).isEqualTo(40);
        assertThat(Day15.chiton(new Scanner(input), Day15::repeat)).isEqualTo(315);

    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day15.chiton(scanner)).isEqualTo(537);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day15.chiton(scanner, Day15::repeat)).isEqualTo(2881);
    }

}
