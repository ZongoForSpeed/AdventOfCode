package com.adventofcode.year2022;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import org.apache.commons.collections4.SetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day04 {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    private Day04() {
        // No-Op
    }

    static List<ElfAssignment> readAssignments(Scanner scanner) {
        List<ElfAssignment> assignments = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ElfAssignment assignment = ElfAssignment.readAssignment(line);
            assignments.add(assignment);
        }
        return assignments;
    }

    /**
     * --- Day 4: Camp Cleanup ---
     * <p>
     * Space needs to be cleared before the last supplies can be unloaded from the
     * ships, and so several Elves have been assigned the job of cleaning up
     * sections of the camp. Every section has a unique ID number, and each Elf is
     * assigned a range of section IDs.
     * <p>
     * However, as some of the Elves compare their section assignments with each
     * other, they've noticed that many of the assignments overlap. To try to
     * quickly find overlaps and reduce duplicated effort, the Elves pair up and
     * make a big list of the section assignments for each pair (your puzzle
     * input).
     * <p>
     * For example, consider the following list of section assignment pairs:
     * <p>
     * 2-4,6-8
     * 2-3,4-5
     * 5-7,7-9
     * 2-8,3-7
     * 6-6,4-6
     * 2-6,4-8
     * <p>
     * For the first few pairs, this list means:
     * <p>
     * - Within the first pair of Elves, the first Elf was assigned sections
     * 2-4 (sections 2, 3, and 4), while the second Elf was assigned sections
     * 6-8 (sections 6, 7, 8).
     * - The Elves in the second pair were each assigned two sections.
     * - The Elves in the third pair were each assigned three sections: one got
     * sections 5, 6, and 7, while the other also got 7, plus 8 and 9.
     * <p>
     * This example list uses single-digit section IDs to make it easier to draw;
     * your actual list might contain larger numbers. Visually, these pairs of
     * section assignments look like this:
     * <p>
     * .234.....  2-4
     * .....678.  6-8
     * <p>
     * .23......  2-3
     * ...45....  4-5
     * <p>
     * ....567..  5-7
     * ......789  7-9
     * <p>
     * .2345678.  2-8
     * ..34567..  3-7
     * <p>
     * .....6...  6-6
     * ...456...  4-6
     * <p>
     * .23456...  2-6
     * ...45678.  4-8
     * <p>
     * Some of the pairs have noticed that one of their assignments fully contains
     * the other. For example, 2-8 fully contains 3-7, and 6-6 is fully contained
     * by 4-6. In pairs where one assignment fully contains the other, one Elf in
     * the pair would be exclusively cleaning sections their partner will already
     * be cleaning, so these seem like the most in need of reconsideration. In
     * this example, there are 2 such pairs.
     * <p>
     * In how many assignment pairs does one range fully contain the other?
     */
    static final class PartOne {
        private PartOne() {
            // No-Op
        }

        static long countFullyContained(Scanner scanner) {
            List<ElfAssignment> elfAssignments = readAssignments(scanner);

            return elfAssignments.stream().filter(ElfAssignment::fullyContained).count();
        }

    }

    /**
     * --- Part Two ---
     * <p>
     * It seems like there is still quite a bit of duplicate work planned.
     * Instead, the Elves would like to know the number of pairs that overlap at
     * all.
     * <p>
     * In the above example, the first two pairs (2-4,6-8 and 2-3,4-5) don't
     * overlap, while the remaining four pairs (5-7,7-9, 2-8,3-7, 6-6,4-6, and
     * 2-6,4-8) do overlap:
     * <p>
     * - 5-7,7-9 overlaps in a single section, 7.
     * - 2-8,3-7 overlaps all of the sections 3 through 7.
     * - 6-6,4-6 overlaps in a single section, 6.
     * - 2-6,4-8 overlaps in sections 4, 5, and 6.
     * <p>
     * So, in this example, the number of overlapping assignment pairs is 4.
     * <p>
     * In how many assignment pairs do the ranges overlap?
     */
    static final class PartTwo {
        private PartTwo() {
            // No-Op
        }

        static long countOverlaps(Scanner scanner) {
            List<ElfAssignment> elfAssignments = readAssignments(scanner);
            return elfAssignments.stream().filter(ElfAssignment::overlaps).count();
        }
    }

    record ElfAssignment(IntSet elf1, IntSet elf2) {

        private static ElfAssignment readAssignment(String line) {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                int x1 = Integer.parseInt(matcher.group(1));
                int y1 = Integer.parseInt(matcher.group(2));
                int x2 = Integer.parseInt(matcher.group(3));
                int y2 = Integer.parseInt(matcher.group(4));

                IntSet range1 = IntSets.fromTo(x1, y1 + 1);
                IntSet range2 = IntSets.fromTo(x2, y2 + 1);

                return new ElfAssignment(range1, range2);
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        boolean fullyContained() {
            return elf1.containsAll(elf2) || elf2.containsAll(elf1);
        }

        boolean overlaps() {
            return !SetUtils.intersection(elf1, elf2).isEmpty();
        }
    }
}
