package com.adventofcode.year2016;

import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public final class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    private static ShortList checkSum(ShortList input) {
        do {
            ShortList checksum = new ShortArrayList();
            ShortListIterator iterator = input.iterator();
            while (iterator.hasNext()) {
                short a = iterator.nextShort();
                short b = iterator.nextShort();
                checksum.add((short) ((a == b) ? 1 : 0));
            }
            input = checksum;
        } while (input.size() % 2 == 0);

        return input;
    }

    private static ShortList reverseFlip(ShortList input) {
        input = new ShortArrayList(input);
        Collections.reverse(input);
        for (int i = 0; i < input.size(); i++) {
            input.set(i, (short) (1 - input.getShort(i)));
        }
        return input;
    }

    private static ShortList dragonCurve(ShortList input) {
        ShortList result = new ShortArrayList(input);
        result.add((short) 0);
        result.addAll(reverseFlip(input));
        return result;
    }

    /**
     * --- Day 16: Dragon Checksum ---
     * <p>
     * You're done scanning this part of the network, but you've left traces of
     * your presence. You need to overwrite some disks with random-looking data to
     * cover your tracks and update the local security system with a new checksum
     * for those disks.
     * <p>
     * For the data to not be suspicious, it needs to have certain properties;
     * purely random data will be detected as tampering. To generate appropriate
     * random data, you'll need to use a modified dragon curve.
     * <p>
     * Start with an appropriate initial state (your puzzle input). Then, so long
     * as you don't have enough data yet to fill the disk, repeat the following
     * steps:
     * <p>
     * - Call the data you have at this point "a".
     * - Make a copy of "a"; call this copy "b".
     * - Reverse the order of the characters in "b".
     * - In "b", replace all instances of 0 with 1 and all 1s with 0.
     * - The resulting data is "a", then a single 0, then "b".
     * <p>
     * For example, after a single step of this process,
     * <p>
     * - 1 becomes 100.
     * - 0 becomes 001.
     * - 11111 becomes 11111000000.
     * - 111100001010 becomes 1111000010100101011110000.
     * <p>
     * Repeat these steps until you have enough data to fill the desired disk.
     * <p>
     * Once the data has been generated, you also need to create a checksum of
     * that data. Calculate the checksum only for the data that fits on the disk,
     * even if you generated more data than that in the previous step.
     * <p>
     * The checksum for some given data is created by considering each non-
     * overlapping pair of characters in the input data. If the two characters
     * match (00 or 11), the next checksum character is a 1. If the characters do
     * not match (01 or 10), the next checksum character is a 0. This should
     * produce a new string which is exactly half as long as the original. If the
     * length of the checksum is even, repeat the process until you end up with a
     * checksum with an odd length.
     * <p>
     * For example, suppose we want to fill a disk of length 12, and when we
     * finally generate a string of at least length 12, the first 12 characters
     * are 110010110100. To generate its checksum:
     * <p>
     * - Consider each pair: 11, 00, 10, 11, 01, 00.
     * - These are same, same, different, same, different, same, producing 110101.
     * - The resulting string has length 6, which is even, so we repeat the process.
     * - The pairs are 11 (same), 01 (different), 01 (different).
     * - This produces the checksum 100, which has an odd length, so we stop.
     * <p>
     * Therefore, the checksum for 110010110100 is 100.
     * <p>
     * Combining all of these steps together, suppose you want to fill a disk of
     * length 20 using an initial state of 10000:
     * <p>
     * - Because 10000 is too short, we first use the modified dragon curve to
     * make it longer.
     * - After one round, it becomes 10000011110 (11 characters), still too
     * short.
     * - After two rounds, it becomes 10000011110010000111110 (23 characters),
     * which is enough.
     * - Since we only need 20, but we have 23, we get rid of all but the first
     * 20 characters: 10000011110010000111.
     * - Next, we start calculating the checksum; after one round, we have
     * 0111110101, which 10 characters long (even), so we continue.
     * - After two rounds, we have 01100, which is 5 characters long (odd), so
     * we are done.
     * <p>
     * In this example, the correct checksum would therefore be 01100.
     * <p>
     * The first disk you have to fill has length 272. Using the initial state in
     * your puzzle input, what is the correct checksum?
     * <p>
     * Your puzzle input is 10111100110001111.
     * <p>
     * Your puzzle answer was 11100110111101110.
     * <p>
     * --- Part Two ---
     * <p>
     * The second disk you have to fill has length 35651584. Again using the
     * initial state in your puzzle input, what is the correct checksum for this
     * disk?
     * <p>
     * Your puzzle answer was 10001101010000101.
     */
    static String dragonChecksum(String input, int diskSize) {
        ShortList curve = new ShortArrayList();
        for (int i = 0; i < input.length(); i++) {
            curve.add((short) (input.charAt(i) - '0'));
        }

        while (curve.size() < diskSize) {
            curve = dragonCurve(curve);
        }

        curve = curve.subList(0, diskSize);
        ShortList checksum = checkSum(curve);
        StringBuilder sb = new StringBuilder();
        checksum.forEach(sb::append);

        LOGGER.info("Dragon Checksum for '{}' with disk size {}: {}", input, diskSize, sb);
        return sb.toString();
    }
}
