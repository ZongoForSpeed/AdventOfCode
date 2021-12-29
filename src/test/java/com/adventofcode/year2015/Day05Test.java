package com.adventofcode.year2015;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);
    private static final List<String> FORBIDDEN_STRINGS = List.of("ab", "cd", "pq", "xy");

    private static boolean niceStringPartOne(String input) {
        long countVowels = input.chars().filter(i -> i == 'a' || i == 'e' || i == 'i' || i == 'o' || i == 'u').count();
        if (countVowels < 3) {
            return false;
        }

        if (FORBIDDEN_STRINGS.stream().anyMatch(input::contains)) {
            return false;
        }

        char[] charArray = input.toCharArray();
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i - 1] == charArray[i]) {
                return true;
            }
        }

        return false;
    }

    private static long niceStrings(Scanner scanner, Predicate<String> predicate) {
        long count = 0;
        while (scanner.hasNextLine()) {
            if (predicate.test(scanner.nextLine())) {
                count++;
            }
        }

        return count;
    }


    private static boolean niceStringPartTwo(String input) {
        char[] charArray = input.toCharArray();
        boolean foundRule = false;
        for (int i = 1; i < charArray.length; i++) {
            String s = "" + charArray[i - 1] + charArray[i];
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

        for (int i = 2; i < charArray.length; i++) {
            if (charArray[i - 2] == charArray[i]) {
                return true;
            }
        }

        return false;
    }

    @Test
    void inputExample() {
        assertThat(niceStringPartOne("ugknbfddgicrmopn")).isTrue();
        assertThat(niceStringPartOne("aaa")).isTrue();
        assertThat(niceStringPartOne("jchzalrnumimnmhp")).isFalse();
        assertThat(niceStringPartOne("haegwjzuvuyypxyu")).isFalse();
        assertThat(niceStringPartOne("dvszwmarrgswjxmb")).isFalse();


        assertThat(niceStringPartTwo("qjhvhtzxzqqjkmpb")).isTrue();
        assertThat(niceStringPartTwo("xxyxx")).isTrue();
        assertThat(niceStringPartTwo("uurcxstgmygtbstg")).isFalse();
        assertThat(niceStringPartTwo("ieodomkazucvgmuy")).isFalse();
    }

    /**
     * --- Day 5: Doesn't He Have Intern-Elves For This? ---
     *
     * Santa needs help figuring out which strings in his text file are naughty or nice.
     *
     * A nice string is one with all of the following properties:
     *
     *   - It contains at least three vowels (aeiou only), like aei, xazegov, or
     *     aeiouaeiouaeiou.
     *   - It contains at least one letter that appears twice in a row, like xx,
     *     abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
     *   - It does not contain the strings ab, cd, pq, or xy, even if they are
     *     part of one of the other requirements.
     *
     *  For example:
     *
     *   - ugknbfddgicrmopn is nice because it has at least three vowels
     *     (u...i...o...), a double letter (...dd...), and none of the disallowed
     *     substrings.
     *   - aaa is nice because it has at least three vowels and a double letter,
     *     even though the letters used by different rules overlap.
     *   - jchzalrnumimnmhp is naughty because it has no double letter.
     *   - haegwjzuvuyypxyu is naughty because it contains the string xy.
     *   - dvszwmarrgswjxmb is naughty because it contains only one vowel.
     *
     * How many strings are nice?
     *
     * Your puzzle answer was 238.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2015/day/5/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(niceStrings(scanner, Day05Test::niceStringPartOne)).isEqualTo(238);
        }
    }

    /**
     * --- Part Two ---
     *
     * Realizing the error of his ways, Santa has switched to a better model of
     * determining whether a string is naughty or nice. None of the old rules
     * apply, as they are all clearly ridiculous.
     *
     * Now, a nice string is one with all of the following properties:
     *
     *   - It contains a pair of any two letters that appears at least twice in
     *     the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but
     *     not like aaa (aa, but it overlaps).
     *   - It contains at least one letter which repeats with exactly one letter
     *     between them, like xyx, abcdefeghi (efe), or even aaa.
     *
     * For example:
     *
     *   - qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj)
     *     and a letter that repeats with exactly one letter between them (zxz).
     *   - xxyxx is nice because it has a pair that appears twice and a letter
     *     that repeats with one between, even though the letters used by each
     *     rule overlap.
     *   - uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat
     *     with a single letter between them.
     *   - ieodomkazucvgmuy is naughty because it has a repeating letter with one
     *     between (odo), but no pair that appears twice.
     *
     * How many strings are nice under these new rules?
     *
     * Your puzzle answer was 69.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day05Test.class.getResourceAsStream("/2015/day/5/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(niceStrings(scanner, Day05Test::niceStringPartTwo)).isEqualTo(69);
        }
    }
}
