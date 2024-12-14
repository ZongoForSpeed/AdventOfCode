package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test extends AbstractTest {
    Day02Test() {
        super(2018, 2);
    }

    @Test
    void inputExample() {
        String input = """
                abcdef
                bababc
                abbcde
                abcccd
                aabcdd
                abcdee
                ababab""";

        Scanner scanner = new Scanner(input);
        assertThat(Day02.checksum(scanner)).isEqualTo(12);

        scanner = new Scanner("""
                abcde
                fghij
                klmno
                pqrst
                fguij
                axcye
                wvxyz""");

        assertThat(Day02.inventoryManagementSystem(scanner)).hasValue("fgij");
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day02.checksum(scanner)).isEqualTo(5166);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.inventoryManagementSystem(scanner)).hasValue("cypueihajytordkgzxfqplbwn");
    }

}
