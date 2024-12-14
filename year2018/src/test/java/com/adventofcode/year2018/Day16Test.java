package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test extends AbstractTest {
    Day16Test() {
        super(2018, 16);
    }

    @Test
    void example() {
        String input = """
                Before: [3, 2, 1, 1]
                9 2 1 2
                After:  [3, 2, 2, 1]
                
                """;
        Scanner scanner = new Scanner(input);
        assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(1);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day16.chronalClassificationPartOne(scanner)).isEqualTo(580);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day16.chronalClassificationPartTwo(scanner)).isEqualTo(537);
    }

}
