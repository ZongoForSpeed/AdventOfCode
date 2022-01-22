package com.adventofcode.year2016;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day20 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20.class);

    private static Stream<Range> excludeIPs(Scanner scanner, long upper) {
        List<Range> exclusions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            exclusions.add(Range.of(scanner.nextLine()));
        }

        LOGGER.info("Exclusions: {}", exclusions);
        Stream<Range> rangeStream = Stream.of(Range.of(0, upper));
        for (Range exclusion : exclusions) {
            rangeStream = rangeStream.flatMap(r -> r.remove(exclusion));
        }

        return rangeStream;
    }

    /**
     * --- Day 20: Firewall Rules ---
     *
     * You'd like to set up a small hidden computer here so you can use it to get
     * back into the network later. However, the corporate firewall only allows
     * communication with certain external IP addresses.
     *
     * You've retrieved the list of blocked IPs from the firewall, but the list
     * seems to be messy and poorly maintained, and it's not clear which IPs are
     * allowed. Also, rather than being written in dot-decimal notation, they are
     * written as plain 32-bit integers, which can have any value from 0 through
     * 4294967295, inclusive.
     *
     * For example, suppose only the values 0 through 9 were valid, and that you
     * retrieved the following blacklist:
     *
     * 5-8
     * 0-2
     * 4-7
     *
     * The blacklist specifies ranges of IPs (inclusive of both the start and end
     * value) that are not allowed. Then, the only IPs that this firewall allows
     * are 3 and 9, since those are the only numbers not in any range.
     *
     * Given the list of blocked IPs you retrieved from the firewall (your puzzle
     * input), what is the lowest-valued IP that is not blocked?
     *
     * Your puzzle answer was 4793564.
     */
    static long findFirstIP(Scanner scanner, long upper) {
        return excludeIPs(scanner, upper).mapToLong(Range::lower).min().orElseThrow();
    }

    /**
     * --- Part Two ---
     *
     * How many IPs are allowed by the blacklist?
     *
     * Your puzzle answer was 19984714.
     */
    static long countIPs(Scanner scanner, long upper) {
        return excludeIPs(scanner, upper).mapToLong(Range::size).sum();
    }

    record Range(long lower, long upper) {
        public static Range of(long lower, long upper) {
            return new Range(lower, upper);
        }

        public static Range of(String input) {
            long[] longs = Arrays.stream(input.split("-")).mapToLong(Long::parseLong).toArray();
            return new Range(longs[0], longs[1]);
        }

        public boolean intersect(Range range) {
            long lowerMax = Math.max(lower, range.lower);
            long upperMin = Math.min(upper, range.upper);

            return lowerMax <= upperMin;
        }

        public Stream<Range> remove(Range range) {
            if (!intersect(range)) {
                return Stream.of(this);
            }

            long lowerMin = Math.min(lower, range.lower);
            long lowerMax = Math.max(lower, range.lower);
            long upperMin = Math.min(upper, range.upper);
            long upperMax = Math.max(upper, range.upper);

            return Stream.of(
                    new Range(lowerMin, lowerMax - 1),
                    new Range(upperMin + 1, upperMax)
            ).filter(Range::valid).filter(this::intersect);
        }

        public boolean valid() {
            return upper >= lower;
        }

        public long size() {
            return upper - lower + 1;
        }
    }
}
