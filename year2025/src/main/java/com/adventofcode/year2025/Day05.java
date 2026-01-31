package com.adventofcode.year2025;

import com.adventofcode.common.point.range.Range;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day05 {
    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)");

    /**
     * --- Day 5: Cafeteria ---
     * <p>
     * As the forklifts break through the wall, the Elves are delighted to
     * discover that there was a cafeteria on the other side after all.
     * <p>
     * You can hear a commotion coming from the kitchen. "At this rate, we won't
     * have any time left to put the wreaths up in the dining hall!" Resolute in
     * your quest, you investigate.
     * <p>
     * "If only we hadn't switched to the new inventory management system right
     * before Christmas!" another Elf exclaims. You ask what's going on.
     * <p>
     * The Elves in the kitchen explain the situation: because of their
     * complicated new inventory management system, they can't figure out which of
     * their ingredients are fresh and which are spoiled. When you ask how it
     * works, they give you a copy of their database (your puzzle input).
     * <p>
     * The database operates on ingredient IDs. It consists of a list of fresh
     * ingredient ID ranges, a blank line, and a list of available ingredient IDs.
     * For example:
     * <p>
     * 3-5
     * 10-14
     * 16-20
     * 12-18
     * <p>
     * 1
     * 5
     * 8
     * 11
     * 17
     * 32
     * <p>
     * The fresh ID ranges are inclusive: the range 3-5 means that ingredient IDs
     * 3, 4, and 5 are all fresh. The ranges can also overlap; an ingredient ID is
     * fresh if it is in any range.
     * <p>
     * The Elves are trying to determine which of the available ingredient IDs are
     * fresh. In this example, this is done as follows:
     * <p>
     * Ingredient ID 1 is spoiled because it does not fall into any range.
     * Ingredient ID 5 is fresh because it falls into range 3-5.
     * Ingredient ID 8 is spoiled.
     * Ingredient ID 11 is fresh because it falls into range 10-14.
     * Ingredient ID 17 is fresh because it falls into range 16-20 as well as
     * range 12-18.
     * Ingredient ID 32 is spoiled.
     * <p>
     * So, in this example, 3 of the available ingredient IDs are fresh.
     * <p>
     * Process the database file from the new inventory management system. How
     * many of the available ingredient IDs are fresh?
     * <p>
     * --- Part Two ---
     * <p>
     * The Elves start bringing their spoiled inventory to the trash chute at the
     * back of the kitchen.
     * <p>
     * So that they can stop bugging you when they get new inventory, the Elves
     * would like to know all of the IDs that the fresh ingredient ID ranges
     * consider to be fresh. An ingredient ID is still considered fresh if it is
     * in any range.
     * <p>
     * Now, the second section of the database (the available ingredient IDs) is
     * irrelevant. Here are the fresh ingredient ID ranges from the above example:
     * <p>
     * 3-5
     * 10-14
     * 16-20
     * 12-18
     * <p>
     * The ingredient IDs that these ranges consider to be fresh are 3, 4, 5, 10,
     * 11, 12, 13, 14, 15, 16, 17, 18, 19, and 20. So, in this example, the fresh
     * ingredient ID ranges consider a total of 14 ingredient IDs to be fresh.
     * <p>
     * Process the database file again. How many ingredient IDs are considered to
     * be fresh according to the fresh ingredient ID ranges?
     */
    private Day05() {
        // No-Op
    }

    public static int partOne(Scanner scanner) {
        List<Range> ranges = new ArrayList<>();
        LongList ids = new LongArrayList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                long first = Long.parseLong(matcher.group(1));
                long second = Long.parseLong(matcher.group(2));
                ranges.add(new Range(first, second));
            } else if (!line.isEmpty()) {
                ids.add(Long.parseLong(line));
            }
        }

        int fresh = 0;
        for (long id : ids) {
            if (ranges.stream().anyMatch(r -> r.contains(id))) {
                fresh++;
            }
        }
        return fresh;
    }

    public static long partTwo(Scanner scanner) {
        List<Range> ranges = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                long first = Long.parseLong(matcher.group(1));
                long second = Long.parseLong(matcher.group(2));
                ranges.add(new Range(first, second));
            } else {
                break;
            }
        }

        if (ranges.isEmpty()) {
            return 0;
        }

        ranges.sort(Comparator.comparingLong(Range::lower));

        List<Range> merged = new ArrayList<>();
        Range current = ranges.getFirst();

        for (int i = 1; i < ranges.size(); i++) {
            Range next = ranges.get(i);
            if (Range.intersect(current, next)) {
                current = Range.union(current, next);
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);

        return merged.stream()
                .mapToLong(Range::size)
                .sum();
    }
}
