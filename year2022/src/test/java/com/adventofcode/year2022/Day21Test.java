package com.adventofcode.year2022;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2022, 21);
    }

    @Test
    void testExample() {
        String input = """
                root: pppw + sjmn
                dbpl: 5
                cczh: sllz + lgvd
                zczc: 2
                ptdq: humn - dvpt
                dvpt: 3
                lfqf: 4
                humn: 5
                ljgn: 2
                sjmn: drzm * dbpl
                sllz: 4
                pppw: cczh / lfqf
                lgvd: ljgn * ptdq
                drzm: hmdt - zczc
                hmdt: 32""";

        {
            Scanner scanner = new Scanner(input);
            long shout = Day21.partOne(scanner);
            assertThat(shout).isEqualTo(152);
        }

        {
            Scanner scanner = new Scanner(input);
            long shout = Day21.partTwo(scanner);
            assertThat(shout).isEqualTo(301);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day21.partOne(scanner)).isEqualTo(72664227897438L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day21.partTwo(scanner)).isEqualTo(3916491093817L);
    }

}
