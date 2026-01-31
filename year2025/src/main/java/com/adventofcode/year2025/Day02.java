package com.adventofcode.year2025;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.apache.commons.lang3.Strings;

import java.util.Scanner;
import java.util.function.LongPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day02 {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)");

    /**
     * --- Day 2: Gift Shop ---
     * <p>
     * You get inside and take the elevator to its only other stop: the gift shop.
     * "Thank you for visiting the North Pole!" gleefully exclaims a nearby sign.
     * You aren't sure who is even allowed to visit the North Pole, but you know
     * you can access the lobby through here, and from there you can access the
     * rest of the North Pole base.
     * <p>
     * As you make your way through the surprisingly extensive selection, one of
     * the clerks recognizes you and asks for your help.
     * <p>
     * As it turns out, one of the younger Elves was playing on a gift shop
     * computer and managed to add a whole bunch of invalid product IDs to their
     * gift shop database! Surely, it would be no trouble for you to identify the
     * invalid product IDs for them, right?
     * <p>
     * They've even checked most of the product ID ranges already; they only have
     * a few product ID ranges (your puzzle input) that you'll need to check. For
     * example:
     * <p>
     * 11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
     * 1698522-1698528,446443-446449,38593856-38593862,565653-565659,
     * 824824821-824824827,2121212118-2121212124
     * <p>
     * (The ID ranges are wrapped here for legibility; in your input, they appear
     * on a single long line.)
     * <p>
     * The ranges are separated by commas (,); each range gives its first ID and
     * last ID separated by a dash (-).
     * <p>
     * Since the young Elf was just doing silly patterns, you can find the invalid
     * IDs by looking for any ID which is made only of some sequence of digits
     * repeated twice. So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice)
     * would all be invalid IDs.
     * <p>
     * None of the numbers have leading zeroes; 0101 isn't an ID at all. (101 is a
     * valid ID that you would ignore.)
     * <p>
     * Your job is to find all of the invalid IDs that appear in the given ranges.
     * In the above example:
     * <p>
     * 11-22 has two invalid IDs, 11 and 22.
     * 95-115 has one invalid ID, 99.
     * 998-1012 has one invalid ID, 1010.
     * 1188511880-1188511890 has one invalid ID, 1188511885.
     * 222220-222224 has one invalid ID, 222222.
     * 1698522-1698528 contains no invalid IDs.
     * 446443-446449 has one invalid ID, 446446.
     * 38593856-38593862 has one invalid ID, 38593859.
     * The rest of the ranges contain no invalid IDs.
     * <p>
     * Adding up all the invalid IDs in this example produces 1227775554.
     * <p>
     * What do you get if you add up all of the invalid IDs?
     *  --- Part Two ---
     * <p>
     * The clerk quickly discovers that there are still invalid IDs in the ranges
     * in your list. Maybe the young Elf was doing other silly patterns as well?
     * <p>
     * Now, an ID is invalid if it is made only of some sequence of digits
     * repeated at least twice. So, 12341234 (1234 two times), 123123123 (123
     * three times), 1212121212 (12 five times), and 1111111 (1 seven times) are
     * all invalid IDs.
     * <p>
     * From the same example as before:
     * <p>
     * 11-22 still has two invalid IDs, 11 and 22.
     * 95-115 now has two invalid IDs, 99 and 111.
     * 998-1012 now has two invalid IDs, 999 and 1010.
     * 1188511880-1188511890 still has one invalid ID, 1188511885.
     * 222220-222224 still has one invalid ID, 222222.
     * 1698522-1698528 still contains no invalid IDs.
     * 446443-446449 still has one invalid ID, 446446.
     * 38593856-38593862 still has one invalid ID, 38593859.
     * 565653-565659 now has one invalid ID, 565656.
     * 824824821-824824827 now has one invalid ID, 824824824.
     * 2121212118-2121212124 now has one invalid ID, 2121212121.
     * <p>
     * Adding up all the invalid IDs in this example produces 4174379265.
     * <p>
     * What do you get if you add up all of the invalid IDs using these new rules?
     */
    private Day02() {
        // No-Op
    }

    public static long partOne(Scanner scanner) {
        return checkGiftShop(scanner, Day02::invalidPartOne);
    }

    public static long partTwo(Scanner scanner) {
        return checkGiftShop(scanner, Day02::invalidPartTwo);
    }

    private static long checkGiftShop(Scanner scanner, LongPredicate predicate) {
        LongSet invalidIds = new LongOpenHashSet();

        for (String s : Splitter.on(',').split(scanner.nextLine())) {
            Matcher matcher = PATTERN.matcher(s);
            if (matcher.matches()) {
                long id1 = Long.parseLong(matcher.group(1));
                long id2 = Long.parseLong(matcher.group(2));

                for (long i = id1; i <= id2; ++i) {
                    if (predicate.test(i)) {
                        invalidIds.add(i);
                    }
                }
            }
        }

        return invalidIds.longStream().sum();
    }

    private static boolean invalidPartOne(long id) {
        String s = Long.toString(id);
        int length = s.length();
        return length % 2 == 0 && Strings.CS.equals(s.substring(0, length / 2), s.substring(length / 2));
    }

    private static boolean invalidPartTwo(long id) {
        String s = Long.toString(id);
        var new_string = s + s;
        new_string = new_string.substring(1, 2 * s.length() - 1);
        return Strings.CS.contains(new_string, s);
    }

}
