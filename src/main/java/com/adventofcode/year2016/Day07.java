package com.adventofcode.year2016;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern PATTERN = Pattern.compile("([a-z]+)\\[([a-z]+)](.*)");

    private Day07() {
        // No-Op
    }

    static boolean abba(String input) {
        char[] chars = input.toCharArray();
        for (int i = 3; i < chars.length; i++) {
            if (chars[i - 3] == chars[i] && chars[i - 1] == chars[i - 2] && chars[i - 3] != chars[i - 2]) {
                return true;
            }
        }

        return false;
    }

    static List<String> findABA(String input) {
        List<String> aba = new ArrayList<>();
        char[] chars = input.toCharArray();
        for (int i = 2; i < chars.length; i++) {
            if (chars[i - 2] == chars[i] && chars[i - 1] != chars[i - 2]) {
                aba.add(input.substring(i - 2, i + 1));

            }
        }

        return aba;
    }

    static String correspondingBAB(String aba) {
        if (aba.length() != 3) {
            throw new IllegalStateException("String '" + aba + "' is not an ABA");
        }

        return "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
    }

    static boolean supportTLS(String ipV7) {
        List<String> outsideBrackets = new ArrayList<>();
        List<String> insideBrackets = new ArrayList<>();

        if (!readBrackets(ipV7, outsideBrackets, insideBrackets)) {
            return false;
        }

        return outsideBrackets.stream().anyMatch(Day07::abba)
               && insideBrackets.stream().noneMatch(Day07::abba);
    }

    static boolean supportSSL(String ipV7) {
        List<String> outsideBrackets = new ArrayList<>();
        List<String> insideBrackets = new ArrayList<>();

        if (!readBrackets(ipV7, outsideBrackets, insideBrackets)) {
            return false;
        }

        List<String> bab = outsideBrackets.stream()
                .map(Day07::findABA)
                .flatMap(Collection::stream)
                .map(Day07::correspondingBAB)
                .toList();

        return insideBrackets.stream().anyMatch(s -> bab.stream().anyMatch(s::contains));
    }

    private static boolean readBrackets(String ipV7, List<String> outsideBrackets, List<String> insideBrackets) {
        Matcher matcher = PATTERN.matcher(ipV7);
        if (!matcher.matches()) {
            return false;
        }

        String group;
        do {
            outsideBrackets.add(matcher.group(1));
            insideBrackets.add(matcher.group(2));
            group = matcher.group(3);
            matcher = PATTERN.matcher(group);
        } while (matcher.matches());

        outsideBrackets.add(group);

        LOGGER.info("outsideBrackets: {}", outsideBrackets);
        LOGGER.info("insideBrackets: {}", insideBrackets);
        return true;
    }

    /**
     * --- Day 7: Internet Protocol Version 7 ---
     *
     * While snooping around the local network of EBHQ, you compile a list of
     * IP addresses (they're IPv7, of course; IPv6 is much too limited). You'd
     * like to figure out which IPs support TLS (transport-layer snooping).
     *
     * An IP supports TLS if it has an Autonomous Bridge Bypass Annotation, or
     * ABBA. An ABBA is any four-character sequence which consists of a pair of
     * two different characters followed by the reverse of that pair, such as xyyx
     * or abba. However, the IP also must not have an ABBA within any hypernet
     * sequences, which are contained by square brackets.
     *
     * For example:
     *
     *   - abba[mnop]qrst supports TLS (abba outside square brackets).
     *   - abcd[bddb]xyyx does not support TLS (bddb is within square brackets,
     *     even though xyyx is outside square brackets).
     *   - aaaa[qwer]tyui does not support TLS (aaaa is invalid; the interior
     *     characters must be different).
     *   - ioxxoj[asdfgh]zxcvbn supports TLS (oxxo is outside square brackets,
     *     even though it's within a larger string).
     *
     * How many IPs in your puzzle input support TLS?
     *
     * Your puzzle answer was 118.
     */
    static int supportTLS(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (supportTLS(line)) {
                count++;
            }
        }
        return count;
    }

    /**
     * --- Part Two ---
     * You would also like to know which IPs support SSL (super-secret listening).
     *
     * An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, anywhere
     * in the supernet sequences (outside any square bracketed sections), and a
     * corresponding Byte Allocation Block, or BAB, anywhere in the hypernet
     * sequences. An ABA is any three-character sequence which consists of the
     * same character twice with a different character between them, such as xyx
     * or aba. A corresponding BAB is the same characters but in reversed
     * positions: yxy and bab, respectively.
     *
     * For example:
     *
     *   - aba[bab]xyz supports SSL (aba outside square brackets with
     *     corresponding bab within square brackets).
     *   - xyx[xyx]xyx does not support SSL (xyx, but no corresponding yxy).
     *   - aaa[kek]eke supports SSL (eke in supernet with corresponding kek in
     *     hypernet; the aaa sequence is not related, because the interior character must be different).
     *   - zazbz[bzb]cdb supports SSL (zaz has no corresponding aza, but zbz has
     *     a corresponding bzb, even though zaz and zbz overlap).
     *
     * How many IPs in your puzzle input support SSL?
     *
     * Your puzzle answer was 260.
     */
    static int supportSSL(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (supportSSL(line)) {
                count++;
            }
        }
        return count;
    }
}
