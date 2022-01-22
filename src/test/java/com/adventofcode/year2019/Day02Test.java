package com.adventofcode.year2019;

import com.adventofcode.Intcode;
import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

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

    @Test
    void testInputPartOne() throws IOException {
        String input = FileUtils.readLine("/2019/day/2/input");
        long[] output = Intcode.intcode(input, 12, 2);
        assertThat(output[0]).isEqualTo(3850704);
    }

    @Test
    void testInputPartTwo() throws IOException {
        String input = FileUtils.readLine("/2019/day/2/input");
        assertThat(Day02.solvePuzzle(input, 19690720)).isEqualTo(6718);
    }
}
