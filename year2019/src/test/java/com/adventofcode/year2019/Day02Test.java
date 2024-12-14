package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test extends AbstractTest {
    Day02Test() {
        super(2019, 2);
    }

    @Test
    void testSum() {
        assertThat(Intcode.intcode("1,0,0,0,99")).isEqualTo("2,0,0,0,99");
    }

    @Test
    void testProduct() {
        assertThat(Intcode.intcode("2,3,0,3,99")).isEqualTo("2,3,0,6,99");
        assertThat(Intcode.intcode("2,4,4,5,99,0")).isEqualTo("2,4,4,5,99,9801");
    }

    @Test
    void testMultiple() {
        assertThat(Intcode.intcode("1,1,1,4,99,5,6,0,99")).isEqualTo("30,1,1,4,2,5,6,0,99");
        assertThat(Intcode.intcode("1,9,10,3,2,3,11,0,99,30,40,50")).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50");
    }

    @Override
    public void partOne(Scanner scanner) {
        long[] output = Intcode.intcode(scanner.nextLine(), 12, 2);
        assertThat(output[0]).isEqualTo(3850704);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day02.solvePuzzle(scanner.nextLine(), 19690720)).isEqualTo(6718);
    }
}
