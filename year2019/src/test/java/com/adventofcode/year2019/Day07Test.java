package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.longs.LongList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test extends AbstractTest {
    Day07Test() {
        super(2019, 7);
    }

    @Test
    void example() {
        Assertions.assertThat(Day07.thrusterSignal("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0", LongList.of(4L, 3L, 2L, 1L, 0L))).isEqualTo(43210);
    }

    @Test
    void maxThrusterSignalExample1() {
        Pair<LongList, Long> max = Day07.maxThrusterSignal("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0", 0L, 1L, 2L, 3L, 4L);
        assertThat(max.left().toLongArray()).containsExactly(4L, 3L, 2L, 1L, 0L);
        assertThat(max.right()).isEqualTo(43210);
    }

    @Test
    void maxThrusterSignalExample2() {
        Pair<LongList, Long> max = Day07.maxThrusterSignal("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0", 0L, 1L, 2L, 3L, 4L);
        assertThat(max.left().toLongArray()).containsExactly(0L, 1L, 2L, 3L, 4L);
        assertThat(max.right()).isEqualTo(54321L);
    }

    @Test
    void maxThrusterSignalExample3() {
        Pair<LongList, Long> max = Day07.maxThrusterSignal("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0", 0L, 1L, 2L, 3L, 4L);
        assertThat(max.left().toLongArray()).containsExactly(1L, 0L, 4L, 3L, 2L);
        assertThat(max.right()).isEqualTo(65210);
    }

    @Test
    void feedbackLoop1() {
        long thrusterSignal = Day07.thrusterSignal("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5", LongList.of(9L, 8L, 7L, 6L, 5L));
        assertThat(thrusterSignal).isEqualTo(139629729);
    }

    @Test
    void maxFeedbackLoop1() {
        Pair<LongList, Long> thrusterSignal = Day07.maxThrusterSignal("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5", 9L, 8L, 7L, 6L, 5L);
        assertThat(thrusterSignal.right()).isEqualTo(139629729);
        assertThat(thrusterSignal.left().toLongArray()).containsExactly(9L, 8L, 7L, 6L, 5L);
    }

    @Test
    void feedbackLoop2() {
        long thrusterSignal = Day07.thrusterSignal("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10", LongList.of(9L, 7L, 8L, 5L, 6L));
        assertThat(thrusterSignal).isEqualTo(18216);
    }

    @Test
    void maxFeedbackLoop2() {
        Pair<LongList, Long> thrusterSignal = Day07.maxThrusterSignal("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10", 9L, 8L, 7L, 6L, 5L);
        assertThat(thrusterSignal.right()).isEqualTo(18216);
        assertThat(thrusterSignal.left().toLongArray()).containsExactly(9L, 7L, 8L, 5L, 6L);
    }

    @Override
    public void partOne(Scanner scanner) {
        Pair<LongList, Long> max = Day07.maxThrusterSignal(scanner.nextLine(), 0L, 1L, 2L, 3L, 4L);
        assertThat(max.left().toLongArray()).containsExactly(1L, 3L, 0L, 4L, 2L);
        assertThat(max.right()).isEqualTo(24625);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Pair<LongList, Long> max = Day07.maxThrusterSignal(scanner.nextLine(), 9L, 8L, 7L, 6L, 5L);
        assertThat(max.left().toLongArray()).containsExactly(5L, 8L, 6L, 9L, 7L);
        assertThat(max.right()).isEqualTo(36497698);
    }
}
