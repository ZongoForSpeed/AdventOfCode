package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {

    @Test
    void testDealIntoNewStack() {
        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.dealIntoNewStack(cards).toIntArray()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    }

    @Test
    void testCutCards() {
        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.cutCards(cards, 3).toIntArray()).containsExactly(3, 4, 5, 6, 7, 8, 9, 0, 1, 2);
        assertThat(Day22.cutCards(cards, -4).toIntArray()).containsExactly(6, 7, 8, 9, 0, 1, 2, 3, 4, 5);
    }

    @Test
    void testDealWithIncrement() {
        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.dealWithIncrement(cards, 3).toIntArray()).containsExactly(0, 7, 4, 1, 8, 5, 2, 9, 6, 3);
    }

    @Test
    void testSimpleExample1() {
        List<String> commands = Arrays.asList("deal with increment 7",
                Day22.DEAL_INTO_NEW_STACK,
                Day22.DEAL_INTO_NEW_STACK);

        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        IntList shuffle = Day22.slamShuffle(cards, commands);
        assertThat(shuffle.toIntArray()).containsExactly(0, 3, 6, 9, 2, 5, 8, 1, 4, 7);
    }

    @Test
    void testSimpleExample2() {
        List<String> commands = Arrays.asList("cut 6",
                "deal with increment 7",
                Day22.DEAL_INTO_NEW_STACK);

        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.slamShuffle(cards, commands).toIntArray()).containsExactly(3, 0, 7, 4, 1, 8, 5, 2, 9, 6);
    }

    @Test
    void testSimpleExample3() {
        List<String> commands = Arrays.asList("deal with increment 7",
                "deal with increment 9",
                "cut -2");

        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.slamShuffle(cards, commands).toIntArray()).containsExactly(6, 3, 0, 7, 4, 1, 8, 5, 2, 9);
    }

    @Test
    void testSimpleExample4() {
        List<String> commands = Arrays.asList(Day22.DEAL_INTO_NEW_STACK,
                "cut -2",
                "deal with increment 7",
                "cut 8",
                "cut -4",
                "deal with increment 7",
                "cut 3",
                "deal with increment 9",
                "deal with increment 3",
                "cut -1");

        IntList cards = IntArrayList.toList(IntStream.range(0, 10));
        assertThat(Day22.slamShuffle(cards, commands).toIntArray()).containsExactly(9, 2, 5, 8, 1, 4, 7, 0, 3, 6);
    }

    @Test
    void testInputPartOne() throws IOException {
        List<String> commands = FileUtils.readLines("/2019/day/22/input");
        IntList cards = IntArrayList.toList(IntStream.range(0, 10007));
        IntList shuffle = Day22.slamShuffle(cards, commands);
        assertThat(shuffle.indexOf(2019)).isEqualTo(4775);
        assertThat(Day22.slamShuffle(2019, 10007, commands)).isEqualTo(4775);
        assertThat(Day22.inverseSlamShuffle(commands, 4775, 10007)).isEqualTo(2019);
    }

    @Test
    void testSolvePartTwo() throws IOException {
        List<String> commands = FileUtils.readLines("/2019/day/22/input");
        long result = Day22.inverseSlamShuffle(commands, 2020, 119315717514047L, 101741582076661L);
        assertThat(result).isEqualTo(37889219674304L);
    }
}
