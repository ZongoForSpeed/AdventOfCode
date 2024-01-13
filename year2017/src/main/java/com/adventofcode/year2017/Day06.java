package com.adventofcode.year2017;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Day06 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);

    private Day06() {
        // No-Op
    }

    private static int maxIndex(int[] memory) {
        int index = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < memory.length; i++) {
            int bank = memory[i];
            if (bank > max) {
                index = i;
                max = bank;
            }
        }
        return index;
    }

    private static IntList memoryReallocation(IntList input) {
        int[] memory = input.toIntArray();
        int index = maxIndex(memory);
        int value = memory[index];
        memory[index] = 0;
        do {
            index++;
            index = index % memory.length;
            memory[index]++;
            value--;
        } while (value > 0);
        return IntList.of(memory);
    }

    /**
     * --- Day 6: Memory Reallocation ---
     *
     * A debugger program here is having an issue: it is trying to repair a memory
     * reallocation routine, but it keeps getting stuck in an infinite loop.
     *
     * In this area, there are sixteen memory banks; each memory bank can hold any
     * number of blocks. The goal of the reallocation routine is to balance the
     * blocks between the memory banks.
     *
     * The reallocation routine operates in cycles. In each cycle, it finds the
     * memory bank with the most blocks (ties won by the lowest-numbered memory
     * bank) and redistributes those blocks among the banks. To do this, it
     * removes all of the blocks from the selected bank, then moves to the next
     * (by index) memory bank and inserts one of the blocks. It continues doing
     * this until it runs out of blocks; if it reaches the last memory bank, it
     * wraps around to the first one.
     *
     * The debugger would like to know how many redistributions can be done before
     * a blocks-in-banks configuration is produced that has been seen before.
     *
     * For example, imagine a scenario with only four memory banks:
     *
     *   - The banks start with 0, 2, 7, and 0 blocks. The third bank has the
     *     most blocks, so it is chosen for redistribution.
     *   - Starting with the next bank (the fourth bank) and then continuing to
     *     the first bank, the second bank, and so on, the 7 blocks are spread
     *     out over the memory banks. The fourth, first, and second banks get two
     *     blocks each, and the third bank gets one back. The final result looks
     *     like this: 2 4 1 2.
     *   - Next, the second bank is chosen because it contains the most blocks
     *     (four). Because there are four memory banks, each gets one block. The
     *     result is: 3 1 2 3.
     *   - Now, there is a tie between the first and fourth memory banks, both of
     *     which have three blocks. The first bank wins the tie, and its three
     *     blocks are distributed evenly over the other three banks, leaving it
     *     with none: 0 2 3 4.
     *   - The fourth bank is chosen, and its four blocks are distributed such
     *     that each of the four banks receives one: 1 3 4 1.
     *   - The third bank is chosen, and the same thing happens: 2 4 1 2.
     *
     * At this point, we've reached a state we've seen before: 2 4 1 2 was already
     * seen. The infinite loop is detected after the fifth block redistribution
     * cycle, and so the answer in this example is 5.
     *
     * Given the initial block counts in your puzzle input, how many
     * redistribution cycles must be completed before a configuration is produced
     * that has been seen before?
     *
     * Your puzzle answer was 11137.
     */
    static int memoryReallocationPartOne(String input) {
        IntList memory = IntArrayList.toList(Arrays.stream(input.split("[ \t]")).mapToInt(Integer::parseInt));
        Set<IntList> memories = new HashSet<>();
        memories.add(memory);

        while (true) {
            memory = memoryReallocation(memory);
            if (!memories.add(memory)) {
                int size = memories.size();
                LOGGER.info("Memories: {}", size);
                return size;
            }
        }
    }

    /**
     * --- Part Two ---
     *
     * Now, the jumps are even stranger: after each jump, if the offset was three
     * or more, instead decrease it by 1. Otherwise, increase it by 1 as before.
     *
     * Using this rule with the above example, the process now takes 10 steps, and
     * the offset values after finding the exit are left as 2 3 2 3 -1.
     *
     * How many steps does it now take to reach the exit?
     *
     * Your puzzle answer was 1037.
     */
    static int memoryReallocationPartTwo(String input) {
        IntList memory = IntArrayList.toList(Arrays.stream(input.split("[ \t]")).mapToInt(Integer::parseInt));
        Map<IntList, Integer> memories = new HashMap<>();
        int step = 0;
        memories.put(memory, step);

        while (true) {
            memory = memoryReallocation(memory);
            if (memories.containsKey(memory)) {
                return step - memories.get(memory) + 1;
            }
            memories.put(memory, ++step);
        }
    }
}
