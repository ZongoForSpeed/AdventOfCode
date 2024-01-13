package com.adventofcode.year2015;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Scanner;

public final class Day17 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    private Day17() {
        // No-Op
    }

    /**
     * --- Day 17: No Such Thing as Too Much ---
     *
     * The elves bought too much eggnog again - 150 liters this time. To fit it
     * all into your refrigerator, you'll need to move it into smaller containers.
     * You take an inventory of the capacities of the available containers.
     *
     * For example, suppose you have containers of size 20, 15, 10, 5, and 5
     * liters. If you need to store 25 liters, there are four ways to do it:
     *
     *   - 15 and 10
     *   - 20 and 5 (the first 5)
     *   - 20 and 5 (the second 5)
     *   - 15, 5, and 5
     *
     * Filling all containers entirely, how many different combinations of
     * containers can exactly fit all 150 liters of eggnog?
     *
     * Your puzzle answer was 4372.
     */
    public static long findCombinationsPartOne(Scanner scanner, int size) {
        Long2LongMap combinations = findCombinations(scanner, size);

        return combinations.values().longStream().sum();
    }

    /**
     * --- Part Two ---
     *
     * While playing with all the containers in the kitchen, another load of
     * eggnog arrives! The shipping and receiving department is requesting as many
     * containers as you can spare.
     *
     * Find the minimum number of containers that can exactly fit all 150 liters
     * of eggnog. How many different ways can you fill that number of containers
     * and still hold exactly 150 litres?
     *
     * In the example above, the minimum number of containers was two. There were
     * three ways to use that many containers, and so the answer there would be 3.
     *
     * Your puzzle answer was 4.
     */
    public static long findCombinationsPartTwo(Scanner scanner, int size) {
        Long2LongMap combinations = findCombinations(scanner, size);

        long minContainers = combinations.keySet().longStream().min().orElseThrow();
        return combinations.get(minContainers);
    }

    public static Long2LongMap findCombinations(Scanner scanner, int size) {
        IntList containers = new IntArrayList();
        while (scanner.hasNextLine()) {
            containers.add(Integer.parseInt(scanner.nextLine()));
        }
        LOGGER.info("Containers = {}", containers);

        Long2LongMap combinations = new Long2LongOpenHashMap();
        long maxBitSet = 1L << containers.size();
        for (long c = 1; c < maxBitSet; ++c) {
            BitSet set = BitSet.valueOf(new long[]{c});
            int sum = set.stream().map(containers::getInt).sum();
            if (sum == size) {
                combinations.mergeLong(set.cardinality(), 1L, Long::sum);
            }
        }

        LOGGER.info("Combinations = {}", combinations);
        return combinations;
    }
}
