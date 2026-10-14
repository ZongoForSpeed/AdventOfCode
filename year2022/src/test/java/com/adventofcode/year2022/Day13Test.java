package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 13)
class Day13Test {
    @Test
    void inputExample() {
        var input = """
                [1,1,3,1,1]
                [1,1,5,1,1]
                
                [[1],[2,3,4]]
                [[1],4]
                
                [9]
                [[8,7,6]]
                
                [[4,4],4,4]
                [[4,4],4,4,4]
                
                [7,7,7,7]
                [7,7,7]
                
                []
                [3]
                
                [[[]]]
                [[]]
                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]""";

        {
            var scanner = new Scanner(input);
            assertThat(Day13.partOne(scanner)).isEqualTo(13);
        }

        {
            var scanner = new Scanner(input);
            assertThat(Day13.partTwo(scanner)).isEqualTo(140);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day13.partOne(scanner)).isEqualTo(6235);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day13.partTwo(scanner)).isEqualTo(22866);
    }
}
