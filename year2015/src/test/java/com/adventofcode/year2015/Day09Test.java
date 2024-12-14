package com.adventofcode.year2015;

import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day09Test extends AbstractTest {

    Day09Test() {
        super(2015, 9);
    }

    @Test
    void inputExample() {
        String input = """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141""";

        Scanner scanner = new Scanner(input);

        IntegerPair bestDistance = Day09.computeBestDistance(scanner);
        assertThat(bestDistance.left()).isEqualTo(605);
        assertThat(bestDistance.right()).isEqualTo(982);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day09.computeBestDistance(scanner).left()).isEqualTo(141);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day09.computeBestDistance(scanner).right()).isEqualTo(736);
    }
}
