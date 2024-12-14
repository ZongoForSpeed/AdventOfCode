package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test extends AbstractTest {
    Day11Test() {
        super(2022, 11);
    }

    @Test
    void testExample() {
        String input = """
                Monkey 0:
                  Starting items: 79, 98
                  Operation: new = old * 19
                  Test: divisible by 23
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                
                Monkey 1:
                  Starting items: 54, 65, 75, 74
                  Operation: new = old + 6
                  Test: divisible by 19
                    If true: throw to monkey 2
                    If false: throw to monkey 0
                
                Monkey 2:
                  Starting items: 79, 60, 97
                  Operation: new = old * old
                  Test: divisible by 13
                    If true: throw to monkey 1
                    If false: throw to monkey 3
                
                Monkey 3:
                  Starting items: 74
                  Operation: new = old + 3
                  Test: divisible by 17
                    If true: throw to monkey 0
                    If false: throw to monkey 1""";

        {
            Scanner scanner = new Scanner(input);
            Long reduce = Day11.partOne(scanner);
            assertThat(reduce).isEqualTo(10605L);
        }

        {
            Scanner scanner = new Scanner(input);
            Long reduce = Day11.partTwo(scanner);
            assertThat(reduce).isEqualTo(2713310158L);
        }

    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day11.partOne(scanner)).isEqualTo(118674L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day11.partTwo(scanner)).isEqualTo(32333418600L);
    }
}
