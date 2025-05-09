package com.adventofcode.year2015;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public final class Day05 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);
    private static final List<String> FORBIDDEN_STRINGS = List.of("ab", "cd", "pq", "xy");

    private Day05() {
        // No-Op
    }

    public static long niceStrings(Scanner scanner, Predicate<String> predicate) {
        long count = 0;
        while (scanner.hasNextLine()) {
            if (predicate.test(scanner.nextLine())) {
                count++;
            }
        }

        return count;
    }

    /**
     * --- Day 5: Doesn't He Have Intern-Elves For This? ---
     * <p>
     * Santa needs help figuring out which strings in his text file are naughty or nice.
     * <p>
     * A nice string is one with all of the following properties:
     * <p>
     * - It contains at least three vowels (aeiou only), like aei, xazegov, or
     * aeiouaeiouaeiou.
     * - It contains at least one letter that appears twice in a row, like xx,
     * abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
     * - It does not contain the strings ab, cd, pq, or xy, even if they are
     * part of one of the other requirements.
     * <p>
     * For example:
     * <p>
     * - ugknbfddgicrmopn is nice because it has at least three vowels
     * (u...i...o...), a double letter (...dd...), and none of the disallowed
     * substrings.
     * - aaa is nice because it has at least three vowels and a double letter,
     * even though the letters used by different rules overlap.
     * - jchzalrnumimnmhp is naughty because it has no double letter.
     * - haegwjzuvuyypxyu is naughty because it contains the string xy.
     * - dvszwmarrgswjxmb is naughty because it contains only one vowel.
     * <p>
     * How many strings are nice?
     * <p>
     * Your puzzle answer was 238.
     */
    public static boolean niceStringPartOne(String input) {
        long countVowels = input.chars().filter(i -> i == 'a' || i == 'e' || i == 'i' || i == 'o' || i == 'u').count();
        if (countVowels < 3) {
            return false;
        }

        if (FORBIDDEN_STRINGS.stream().anyMatch(input::contains)) {
            return false;
        }

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == input.charAt(i)) {
                return true;
            }
        }

        return false;
    }

    /**
     * --- Part Two ---
     * <p>
     * Realizing the error of his ways, Santa has switched to a better model of
     * determining whether a string is naughty or nice. None of the old rules
     * apply, as they are all clearly ridiculous.
     * <p>
     * Now, a nice string is one with all of the following properties:
     * <p>
     * - It contains a pair of any two letters that appears at least twice in
     * the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but
     * not like aaa (aa, but it overlaps).
     * - It contains at least one letter which repeats with exactly one letter
     * between them, like xyx, abcdefeghi (efe), or even aaa.
     * <p>
     * For example:
     * <p>
     * - qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj)
     * and a letter that repeats with exactly one letter between them (zxz).
     * - xxyxx is nice because it has a pair that appears twice and a letter
     * that repeats with one between, even though the letters used by each
     * rule overlap.
     * - uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat
     * with a single letter between them.
     * - ieodomkazucvgmuy is naughty because it has a repeating letter with one
     * between (odo), but no pair that appears twice.
     * <p>
     * How many strings are nice under these new rules?
     * <p>
     * Your puzzle answer was 69.
     */
    public static boolean niceStringPartTwo(String input) {
        boolean foundRule = false;
        for (int i = 1; i < input.length(); i++) {
            String s = "" + input.charAt(i - 1) + input.charAt(i);
            int indexOf = StringUtils.indexOf(input, s, i + 1);
            if (indexOf > 0) {
                LOGGER.info("Rule validated with {} and {}/{}", input, s, indexOf);
                foundRule = true;
                break;
            }
        }

        if (!foundRule) {
            return false;
        }

        for (int i = 2; i < input.length(); i++) {
            if (input.charAt(i - 2) == input.charAt(i)) {
                return true;
            }
        }

        return false;
    }
}
