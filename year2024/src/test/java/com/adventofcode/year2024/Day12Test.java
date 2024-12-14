package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test extends AbstractTest {

    protected Day12Test() {
        super(2024, 12);
    }

    @Test
    void inputExample1() {
        String input = """
                AAAA
                BBCD
                BBCC
                EEEC""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(140);
        }

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(80);
        }
    }

    @Test
    void inputExample2() {
        String input = """
                OOOOO
                OXOXO
                OOOOO
                OXOXO
                OOOOO""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(772);
        }


        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(436);
        }
    }

    @Test
    void inputExample3() {
        String input = """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partOne(scanner);

            assertThat(price).isEqualTo(1930);
        }


        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(1206);
        }
    }

    @Test
    void inputExample4() {
        String input = """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(236);
        }
    }

    @Test
    void inputExample5() {
        String input = """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA""";

        try (Scanner scanner = new Scanner(input)) {
            long price = Day12.partTwo(scanner);

            assertThat(price).isEqualTo(368);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day12.partOne(scanner)).isEqualTo(1533644L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day12.partTwo(scanner)).isEqualTo(936718L);
    }

}
