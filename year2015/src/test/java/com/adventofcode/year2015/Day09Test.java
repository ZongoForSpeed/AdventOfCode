package com.adventofcode.year2015;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2015, day = 9)
class Day09Test {

    @Test
    void inputExample() {
        var input = """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141""";

        var scanner = new Scanner(input);

        IntegerPair bestDistance = Day09.computeBestDistance(scanner);
        assertThat(bestDistance.left()).isEqualTo(605);
        assertThat(bestDistance.right()).isEqualTo(982);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day09.computeBestDistance(scanner).left()).isEqualTo(141);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day09.computeBestDistance(scanner).right()).isEqualTo(736);
    }
}
