package com.adventofcode.year2015;

import com.adventofcode.common.maths.Permutations;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);
    private static final Pattern PATTERN = Pattern.compile("(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)\\.");

    private Day13() {
        // No-Op
    }

    public static long computeHappiness(Map<Pair<String, String>, Long> graph, List<String> table, boolean seatYourself) {
        long happiness = 0;
        if (!seatYourself) {
            String first = Iterables.getFirst(table, null);
            String last = Iterables.getLast(table, null);
            happiness = graph.get(Pair.of(first, last)) + graph.get(Pair.of(last, first));
        }

        for (int i = 1; i < table.size(); i++) {
            String person1 = table.get(i - 1);
            String person2 = table.get(i);
            happiness += graph.get(Pair.of(person1, person2)) + graph.get(Pair.of(person2, person1));
        }

        return happiness;
    }

    /**
     * --- Day 13: Knights of the Dinner Table ---
     *
     * In years past, the holiday feast with your family hasn't gone so well. Not
     * everyone gets along! This year, you resolve, will be different. You're
     * going to find the optimal seating arrangement and avoid all those awkward
     * conversations.
     *
     * You start by writing up a list of everyone invited and the amount their
     * happiness would increase or decrease if they were to find themselves
     * sitting next to each other person. You have a circular table that will be
     * just big enough to fit everyone comfortably, and so each person will have
     * exactly two neighbors.
     *
     * For example, suppose you have only four attendees planned, and you
     * calculate their potential happiness as follows:
     *
     * Alice would gain 54 happiness units by sitting next to Bob.
     * Alice would lose 79 happiness units by sitting next to Carol.
     * Alice would lose 2 happiness units by sitting next to David.
     * Bob would gain 83 happiness units by sitting next to Alice.
     * Bob would lose 7 happiness units by sitting next to Carol.
     * Bob would lose 63 happiness units by sitting next to David.
     * Carol would lose 62 happiness units by sitting next to Alice.
     * Carol would gain 60 happiness units by sitting next to Bob.
     * Carol would gain 55 happiness units by sitting next to David.
     * David would gain 46 happiness units by sitting next to Alice.
     * David would lose 7 happiness units by sitting next to Bob.
     * David would gain 41 happiness units by sitting next to Carol.
     *
     * Then, if you seat Alice next to David, Alice would lose 2 happiness units
     * (because David talks so much), but David would gain 46 happiness units
     * (because Alice is such a good listener), for a total change of 44.
     *
     * If you continue around the table, you could then seat Bob next to Alice
     * (Bob gains 83, Alice gains 54). Finally, seat Carol, who sits next to Bob
     * (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41).
     * The arrangement looks like this:
     *
     *      +41 +46
     * +55   David    -2
     * Carol       Alice
     * +60    Bob    +54
     *      -7  +83
     *
     * After trying every other seating arrangement in this hypothetical scenario,
     * you find that this one is the most optimal, with a total change in
     * happiness of 330.
     *
     * What is the total change in happiness for the optimal seating arrangement
     * of the actual guest list?
     *
     * Your puzzle answer was 733.
     *
     * --- Part Two ---
     *
     * In all the commotion, you realize that you forgot to seat yourself. At this
     * point, you're pretty apathetic toward the whole thing, and your happiness
     * wouldn't really go up or down regardless of who you sit next to. You assume
     * everyone else would be just as ambivalent about sitting next to you, too.
     *
     * So, add yourself to the list, and give all happiness relationships that
     * involve you a score of 0.
     *
     * What is the total change in happiness for the optimal seating arrangement
     * that actually includes yourself?
     *
     * Your puzzle answer was 725.
     */
    public static long computeMaxHappiness(Scanner scanner, boolean seatYourself) {
        Map<Pair<String, String>, Long> graph = new HashMap<>();

        Set<String> persons = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String person1 = matcher.group(1);
                String gainLose = matcher.group(2);
                long units = Integer.parseInt(matcher.group(3));
                String person2 = matcher.group(4);
                LOGGER.info("{} -> {} = {} / {}", person1, person2, gainLose, units);
                graph.put(Pair.of(person1, person2), "gain".equals(gainLose) ? units : -units);
                persons.add(person1);
                persons.add(person2);
            } else {
                LOGGER.error("Cannot parse {}", line);
            }
        }

        LOGGER.info("Graph : {}", graph);

        long maxHappiness = Long.MIN_VALUE;

        Iterator<List<String>> iterator = Permutations.of(persons).iterator();
        while (iterator.hasNext()) {
            List<String> permutation = iterator.next();
            long happiness = computeHappiness(graph, permutation, seatYourself);
            if (happiness > maxHappiness) {
                maxHappiness = happiness;
            }
        }
        return maxHappiness;
    }
}
