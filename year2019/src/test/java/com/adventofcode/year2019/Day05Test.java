package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

    @Test
    void testSimple() {
        int randomValue = new Random().nextInt();
        long result = Intcode.ioIntcode("3,0,4,0,99", randomValue);
        assertThat(result).isEqualTo(randomValue);
    }

    @Test
    void testMode() {
        String intcode = Intcode.intcode("1002,4,3,4,33");
        assertThat(intcode).isEqualTo("1002,4,3,4,99");
    }

    @Test
    void testNegative() {
        String intcode = Intcode.intcode("1101,100,-1,4,0");
        assertThat(intcode).isEqualTo("1101,100,-1,4,99");
    }

    @Test
    void testInputPartOne() throws IOException {
        String line = FileUtils.readLine("/2019/day/5/input");
        long result = Day05.diagnosticPartOne(line);
        assertThat(result).isEqualTo(16209841);
    }

    @Test
    void testPositionModeEqual() {
        assertThat(Intcode.ioIntcode("3,9,8,9,10,9,4,9,99,-1,8", 8)).isEqualTo(1);
        assertThat(Intcode.ioIntcode("3,9,8,9,10,9,4,9,99,-1,8", 42)).isZero();
    }

    @Test
    void testPositionModeLess() {
        assertThat(Intcode.ioIntcode("3,9,7,9,10,9,4,9,99,-1,8", 7)).isEqualTo(1);
        assertThat(Intcode.ioIntcode("3,9,7,9,10,9,4,9,99,-1,8", 42)).isZero();
    }

    @Test
    void testImmediateModeEqual() {
        assertThat(Intcode.ioIntcode("3,3,1108,-1,8,3,4,3,99", 8)).isEqualTo(1);
        assertThat(Intcode.ioIntcode("3,3,1108,-1,8,3,4,3,99", 42)).isZero();
    }

    @Test
    void testImmediateModeLess() {
        assertThat(Intcode.ioIntcode("3,3,1107,-1,8,3,4,3,99", 7)).isEqualTo(1);
        assertThat(Intcode.ioIntcode("3,3,1107,-1,8,3,4,3,99", 42)).isZero();
    }

    @Test
    void testPositionJump() {
        assertThat(Intcode.ioIntcode("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0)).isZero();
        assertThat(Intcode.ioIntcode("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 42)).isEqualTo(1);
    }

    @Test
    void testImmediateJump() {
        assertThat(Intcode.ioIntcode("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 0)).isZero();
        assertThat(Intcode.ioIntcode("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 42)).isEqualTo(1);
    }

    @Test
    void testLargerExample() {
        String codes = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        assertThat(Intcode.ioIntcode(codes, 42)).isEqualTo(1001);
        assertThat(Intcode.ioIntcode(codes, 7)).isEqualTo(999);
        assertThat(Intcode.ioIntcode(codes, 8)).isEqualTo(1000);
    }

    @Test
    void testInputPartTwo() throws IOException {
        String line = FileUtils.readLine("/2019/day/5/input");
        assertThat(Day05.diagnosticPartTwo(line)).isEqualTo(8834787);
    }

}
