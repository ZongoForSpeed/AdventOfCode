package com.adventofcode.year2015;

import java.util.HashSet;
import java.util.Set;

public final class Day11 {
    private Day11() {
        // No-Op
    }

    public static long encode(String password) {
        return password.chars().mapToLong(c -> c - 'a').reduce(0L, (a, b) -> a * 26 + b);
    }

    public static String decode(long code) {
        StringBuilder stringBuilder = new StringBuilder();
        while (code > 0) {
            stringBuilder.append((char) (code % 26 + 'a'));
            code /= 26;
        }
        while (stringBuilder.length() < 8) {
            stringBuilder.append('a');
        }

        return stringBuilder.reverse().toString();
    }

    public static boolean validatePassword(String password) {
        if (password.indexOf('i') >= 0 || password.indexOf('o') >= 0 || password.indexOf('l') >= 0) {
            return false;
        }

        boolean findSequence = false;
        char[] charArray = password.toCharArray();
        for (int i = 2; i < charArray.length; i++) {
            char c1 = charArray[i - 2];
            char c2 = charArray[i - 1];
            char c3 = charArray[i];
            if (c1 + 1 == c2 && c2 + 1 == c3) {
                findSequence = true;
                break;
            }
        }

        if (!findSequence) {
            return false;
        }

        Set<String> pairs = new HashSet<>();

        for (int i = 1; i < charArray.length; i++) {
            char c1 = charArray[i - 1];
            char c2 = charArray[i];
            if (c1 == c2) {
                pairs.add("" + c1 + c2);
            }
        }

        return pairs.size() > 1;
    }

    /**
     * --- Day 11: Corporate Policy ---
     *
     * Santa's previous password expired, and he needs help choosing a new one.
     *
     * To help him remember his new password after the old one expires, Santa has
     * devised a method of coming up with a password based on the previous one.
     * Corporate policy dictates that passwords must be exactly eight lowercase
     * letters (for security reasons), so he finds his new password by
     * incrementing his old password string repeatedly until it is valid.
     *
     * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so
     * on. Increase the rightmost letter one step; if it was z, it wraps around to
     * a, and repeat with the next letter to the left until one doesn't wrap
     * around.
     *
     * Unfortunately for Santa, a new Security-Elf recently started, and he has
     * imposed some additional password requirements:
     *
     *   - Passwords must include one increasing straight of at least three
     *     letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip
     *     letters; abd doesn't count.
     *   - Passwords may not contain the letters i, o, or l, as these letters can
     *     be mistaken for other characters and are therefore confusing.
     *   - Passwords must contain at least two different, non-overlapping pairs
     *     of letters, like aa, bb, or zz.
     *
     * For example:
     *
     *   - hijklmmn meets the first requirement (because it contains the straight
     *     hij) but fails the second requirement requirement (because it contains
     *     i and l).
     *   - abbceffg meets the third requirement (because it repeats bb and ff)
     *     but fails the first requirement.
     *   - abbcegjk fails the third requirement, because it only has one double
     *     letter (bb).
     *   - The next password after abcdefgh is abcdffaa.
     *   - The next password after ghijklmn is ghjaabcc, because you eventually
     *     skip all the passwords that start with ghi..., since i is not allowed.
     *
     * Given Santa's current password (your puzzle input), what should his next
     * password be?
     *
     * Your puzzle answer was cqjxxyzz.
     *
     * --- Part Two ---
     *
     * Santa's password expired again. What's the next one?
     *
     * Your puzzle answer was 4666278.
     */
    public static String nextPassword(String password) {
        long code = encode(password);
        while (true) {
            code++;
            String decode = decode(code);
            if (validatePassword(decode)) {
                return decode;
            }
        }
    }
}
