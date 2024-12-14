package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test extends AbstractTest {
    Day02Test() {
        super(2017, 2);
    }

    @Test
    void inputExample() {
        assertThat(Day02.corruptionChecksumPartOne(new Scanner("""
                5 1 9 5
                7 5 3
                2 4 6 8"""))).isEqualTo(18);

        assertThat(Day02.corruptionChecksumPartTwo(new Scanner("""
                5 9 2 8
                9 4 7 3
                3 8 6 5"""))).isEqualTo(9);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day02.corruptionChecksumPartOne(scanner)).isEqualTo(45972);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.corruptionChecksumPartTwo(scanner)).isEqualTo(326);
    }

}
