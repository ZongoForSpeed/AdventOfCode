package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day13Test extends AbstractTest {

    Day13Test() {
        super(2015, 13);
    }

    @Test
    void inputExample() {
        String input = """
                Alice would gain 54 happiness units by sitting next to Bob.
                Alice would lose 79 happiness units by sitting next to Carol.
                Alice would lose 2 happiness units by sitting next to David.
                Bob would gain 83 happiness units by sitting next to Alice.
                Bob would lose 7 happiness units by sitting next to Carol.
                Bob would lose 63 happiness units by sitting next to David.
                Carol would lose 62 happiness units by sitting next to Alice.
                Carol would gain 60 happiness units by sitting next to Bob.
                Carol would gain 55 happiness units by sitting next to David.
                David would gain 46 happiness units by sitting next to Alice.
                David would lose 7 happiness units by sitting next to Bob.
                David would gain 41 happiness units by sitting next to Carol.""";

        Scanner scanner = new Scanner(input);

        assertThat(Day13.computeMaxHappiness(scanner, false)).isEqualTo(330);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day13.computeMaxHappiness(scanner, false)).isEqualTo(733);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day13.computeMaxHappiness(scanner, true)).isEqualTo(725);
    }
}
