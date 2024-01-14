package com.adventofcode.year2019;

import com.adventofcode.common.maths.Digits;
import com.adventofcode.common.utils.IntegerPair;
import com.google.common.collect.Ordering;
import it.unimi.dsi.fastutil.chars.CharList;

import java.util.ArrayList;
import java.util.List;

public final class Day04 {
    private Day04() {
        // No-Op
    }

    /**
     * --- Day 4: Secure Container ---
     * You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the
     * password on a sticky note, but someone threw it out.
     *
     * However, they do remember a few key facts about the password:
     *
     * It is a six-digit number.
     * The value is within the range given in your puzzle input.
     * Two adjacent digits are the same (like 22 in 122345).
     * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or
     * 135679).
     * Other than the range rule, the following are true:
     *
     * 111111 meets these criteria (double 11, never decreases).
     * 223450 does not meet these criteria (decreasing pair of digits 50).
     * 123789 does not meet these criteria (no double).
     * How many different passwords within the range given in your puzzle input meet these criteria?
     *
     * Your puzzle input is 153517-630395.
     */
    public static boolean matchPartOne(int n) {
        CharList digits = Digits.digits(n);
        boolean sorted = Ordering.natural().isOrdered(digits);
        if (!sorted) {
            return false;
        }

        for (int i = 1, digitsSize = digits.size(); i < digitsSize; i++) {
            if (digits.getChar(i) == digits.getChar(i - 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * --- Part Two ---
     * An Elf just remembered one more important detail: the two adjacent matching digits are not part of a larger group
     * of matching digits.
     *
     * Given this additional criterion, but still ignoring the range rule, the following are now true:
     *
     * 112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
     * 123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
     * 111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).
     * How many different passwords within the range given in your puzzle input meet all of the criteria?
     *
     * Your puzzle input is still 153517-630395.
     */
    public static boolean matchPartTwo(int n) {
        CharList digits = Digits.digits(n);
        boolean sorted = Ordering.natural().isOrdered(digits);
        if (!sorted) {
            return false;
        }

        List<IntegerPair> counter = new ArrayList<>();
        IntegerPair current = null;
        for (int value : digits) {
            if (current == null) {
                current = IntegerPair.of(value, 1);
            } else if (current.left() == value) {
                current = IntegerPair.of(value, current.right() + 1);
            } else {
                counter.add(current);
                current = IntegerPair.of(value, 1);
            }
        }
        counter.add(current);

        return counter.stream().anyMatch(p -> p.right() == 2);
    }
}
