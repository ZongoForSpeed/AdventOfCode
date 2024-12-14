package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test extends AbstractTest {
    Day22Test() {
        super(2017, 22);
    }


    @Test
    void inputExample() {
        String input = """
                ..#
                #..
                ...""";

        assertThat(Day22.sporificaVirusPartOne(new Scanner(input), 70)).isEqualTo(41);
        assertThat(Day22.sporificaVirusPartOne(new Scanner(input), 10000)).isEqualTo(5587);

        assertThat(Day22.sporificaVirusPartTwo(new Scanner(input), 100)).isEqualTo(26);
        assertThat(Day22.sporificaVirusPartTwo(new Scanner(input), 10_000_000)).isEqualTo(2511944);
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day22.sporificaVirusPartOne(scanner, 10000)).isEqualTo(5575);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day22.sporificaVirusPartTwo(scanner, 10_000_000)).isEqualTo(2511991);
    }

}
