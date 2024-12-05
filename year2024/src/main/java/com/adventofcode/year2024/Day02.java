package com.adventofcode.year2024;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public final class Day02 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);
    private static final LongSet DIFFS_1 = LongSets.fromTo(1, 4);
    private static final LongSet DIFFS_2 = LongSets.fromTo(-3, 0);

    private Day02() {
        // No-Op
    }

    private static boolean safe(long[] report) {
        LongSet diffs = new LongArraySet();
        for (int j = 1; j < report.length; j++) {
            diffs.add(report[j - 1] - report[j]);
        }

        if (diffs.size() > 4) {
            return false;
        }

        return SetUtils.union(DIFFS_1, diffs).size() == 3 || SetUtils.union(DIFFS_2, diffs).size() == 3;
    }

    private static boolean safe2(long[] report) {
        if (safe(report)) {
            return true;
        }

        for (int i = 0; i < report.length; i++) {
            LongArrayList longs = new LongArrayList(report);
            longs.removeLong(i);
            if (safe(longs.toArray(new long[0]))) {
                return true;
            }
        }

        return false;
    }

    /**
     * --- Day 2: Red-Nosed Reports ---
     * <p>
     * Fortunately, the first location The Historians want to search isn't a long
     * walk from the Chief Historian's office.
     * <p>
     * While the Red-Nosed Reindeer nuclear fusion/fission plant appears to
     * contain no sign of the Chief Historian, the engineers there run up to you
     * as soon as they see you. Apparently, they still talk about the time Rudolph
     * was saved through molecular synthesis from a single electron.
     * <p>
     * They're quick to add that - since you're already here - they'd really
     * appreciate your help analyzing some unusual data from the Red-Nosed
     * reactor. You turn to check if The Historians are waiting for you, but they
     * seem to have already divided into groups that are currently searching every
     * corner of the facility. You offer to help with the unusual data.
     * <p>
     * The unusual data (your puzzle input) consists of many reports, one report
     * per line. Each report is a list of numbers called levels that are separated
     * by spaces. For example:
     * <p>
     * 7 6 4 2 1
     * 1 2 7 8 9
     * 9 7 6 2 1
     * 1 3 2 4 5
     * 8 6 4 4 1
     * 1 3 6 7 9
     * <p>
     * This example data contains six reports each containing five levels.
     * <p>
     * The engineers are trying to figure out which reports are safe. The Red-
     * Nosed reactor safety systems can only tolerate levels that are either
     * gradually increasing or gradually decreasing. So, a report only counts as
     * safe if both of the following are true:
     * <p>
     * - The levels are either all increasing or all decreasing.
     * - Any two adjacent levels differ by at least one and at most three.
     * <p>
     * In the example above, the reports can be found safe or unsafe by checking
     * those rules:
     * <p>
     * - 7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
     * - 1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
     * - 9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
     * - 1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
     * - 8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
     * - 1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
     * <p>
     * So, in this example, 2 reports are safe.
     * <p>
     * Analyze the unusual data from the engineers. How many reports are safe?
     */
    public static int partOne(Scanner scanner) {
        int count = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            long[] report = Splitter.on(' ').splitToStream(line).mapToLong(Long::parseLong).toArray();

            if (safe(report)) {
                LOGGER.info("report {} is safe", line);
                count++;
            } else {
                LOGGER.info("report {} is unsafe", line);
            }
        }
        return count;
    }

    /**
     * --- Part Two ---
     * <p>
     * The engineers are surprised by the low number of safe reports until they
     * realize they forgot to tell you about the Problem Dampener.
     * <p>
     * The Problem Dampener is a reactor-mounted module that lets the reactor
     * safety systems tolerate a single bad level in what would otherwise be a
     * safe report. It's like the bad level never happened!
     * <p>
     * Now, the same rules apply as before, except if removing a single level from
     * an unsafe report would make it safe, the report instead counts as safe.
     * <p>
     * More of the above example's reports are now safe:
     * <p>
     * - 7 6 4 2 1: Safe without removing any level.
     * - 1 2 7 8 9: Unsafe regardless of which level is removed.
     * - 9 7 6 2 1: Unsafe regardless of which level is removed.
     * - 1 3 2 4 5: Safe by removing the second level, 3.
     * - 8 6 4 4 1: Safe by removing the third level, 4.
     * - 1 3 6 7 9: Safe without removing any level.
     * <p>
     * Thanks to the Problem Dampener, 4 reports are actually safe!
     * <p>
     * Update your analysis by handling situations where the Problem Dampener can
     * remove a single level from unsafe reports. How many reports are now safe?
     */
    public static int partTwo(Scanner scanner) {
        int count = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            long[] report = Splitter.on(' ').splitToStream(line).mapToLong(Long::parseLong).toArray();

            if (safe2(report)) {
                LOGGER.info("report {} is safe", line);
                count++;
            } else {
                LOGGER.info("report {} is unsafe", line);
            }
        }
        return count;
    }
}
