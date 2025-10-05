package com.adventofcode.year2020;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    // class: 1-3 or 5-7
    private static final Pattern PATTERN = Pattern.compile("^(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

    private static Triple<Map<String, IntPredicate>, int[], List<int[]>> readNote(List<String> notes) {
        Map<String, IntPredicate> rules = new HashMap<>();
        int[] yourTicket = null;
        List<int[]> nearbyTickets = new ArrayList<>();

        boolean readRules = true;
        boolean readYourTicket = false;
        boolean readNearbyTickets = false;

        for (String note : notes) {
            switch (note) {
                case "" -> {
                    readRules = false;
                    readYourTicket = false;
                    readNearbyTickets = false;
                    continue;
                }
                case "your ticket:" -> {
                    readYourTicket = true;
                    continue;
                }
                case "nearby tickets:" -> {
                    readNearbyTickets = true;
                    continue;
                }
            }

            if (readRules) {
                Matcher matcher = PATTERN.matcher(note);
                if (matcher.find()) {
                    String name = matcher.group(1);
                    int lowerA = Integer.parseInt(matcher.group(2));
                    int upperA = Integer.parseInt(matcher.group(3));
                    int lowerB = Integer.parseInt(matcher.group(4));
                    int upperB = Integer.parseInt(matcher.group(5));
                    List<IntPredicate> predicates = List.of(
                            new SimpleRule(lowerA, upperA),
                            new SimpleRule(lowerB, upperB)
                    );
                    Optional<IntPredicate> predicate = predicates.stream().reduce(IntPredicate::or);
                    rules.put(name, predicate.orElseThrow());
                } else {
                    throw new IllegalStateException("Cannot parse rule: " + note);
                }

            } else if (readYourTicket) {
                yourTicket = Arrays.stream(note.split(",")).mapToInt(Integer::parseInt).toArray();
            } else if (readNearbyTickets) {
                int[] ints = Arrays.stream(note.split(",")).mapToInt(Integer::parseInt).toArray();
                nearbyTickets.add(ints);
            }
        }

        return Triple.of(rules, yourTicket, nearbyTickets);
    }

    static long invalidNearbyTickets(List<String> notes) {
        Triple<Map<String, IntPredicate>, int[], List<int[]>> triple = readNote(notes);
        Map<String, IntPredicate> rules = triple.getLeft();
        int[] yourTicket = triple.getMiddle();
        List<int[]> nearbyTickets = triple.getRight();

        LOGGER.info("Rules {}", rules);
        LOGGER.info("Your ticket {}", yourTicket);
        LOGGER.info("Nearby tickets {}", nearbyTickets);

        IntList invalid = new IntArrayList();
        for (int[] ticket : nearbyTickets) {
            Arrays.stream(ticket)
                    .filter(t -> rules.values().stream().noneMatch(r -> r.test(t)))
                    .forEach(invalid::add);
        }

        LOGGER.info("Invalid {}", invalid);

        return invalid.intStream().mapToLong(t -> t).sum();
    }

    /**
     * --- Day 16: Ticket Translation ---
     * As you're walking to yet another connecting flight, you realize that one of
     * the legs of your re-routed trip coming up is on a high-speed train.
     * However, the train ticket you were given is in a language you don't
     * understand. You should probably figure out what it says before you get to
     * the train station after the next flight.
     * <p>
     * Unfortunately, you can't actually read the words on the ticket. You can,
     * however, read the numbers, and so you figure out the fields these tickets
     * must have and the valid ranges for values in those fields.
     * <p>
     * You collect the rules for ticket fields, the numbers on your ticket, and
     * the numbers on other nearby tickets for the same train service (via the
     * airport security cameras) together into a single document you can reference
     * (your puzzle input).
     * <p>
     * The rules for ticket fields specify a list of fields that exist somewhere
     * on the ticket and the valid ranges of values for each field. For example, a
     * rule like class: 1-3 or 5-7 means that one of the fields in every ticket is
     * named class and can be any value in the ranges 1-3 or 5-7 (inclusive, such
     * that 3 and 5 are both valid in this field, but 4 is not).
     * <p>
     * Each ticket is represented by a single line of comma-separated values. The
     * values are the numbers on the ticket in the order they appear; every ticket
     * has the same format. For example, consider this ticket:
     * <p>
     * .--------------------------------------------------------.
     * | ????: 101    ?????: 102   ??????????: 103     ???: 104 |
     * |                                                        |
     * | ??: 301  ??: 302             ???????: 303      ??????? |
     * | ??: 401  ??: 402           ???? ????: 403    ????????? |
     * '--------------------------------------------------------'
     * <p>
     * Here, ? represents text in a language you don't understand. This ticket
     * might be represented as 101,102,103,104,301,302,303,401,402,403; of course,
     * the actual train tickets you're looking at are much more complicated. In
     * any case, you've extracted just the numbers in such a way that the first
     * number is always the same specific field, the second number is always a
     * different specific field, and so on - you just don't know what each
     * position actually means!
     * <p>
     * Start by determining which tickets are completely invalid; these are
     * tickets that contain values which aren't valid for any field. Ignore your
     * ticket for now.
     * <p>
     * For example, suppose you have the following notes:
     * <p>
     * class: 1-3 or 5-7
     * row: 6-11 or 33-44
     * seat: 13-40 or 45-50
     * <p>
     * your ticket:
     * 7,1,14
     * <p>
     * nearby tickets:
     * 7,3,47
     * 40,4,50
     * 55,2,20
     * 38,6,12
     * <p>
     * It doesn't matter which position corresponds to which field; you can
     * identify invalid nearby tickets by considering only whether tickets contain
     * values that are not valid for any field. In this example, the values on the
     * first nearby ticket are all valid for at least one field. This is not true
     * of the other three nearby tickets: the values 4, 55, and 12 are are not
     * valid for any field. Adding together all of the invalid values produces
     * your ticket scanning error rate: 4 + 55 + 12 = 71.
     * <p>
     * Consider the validity of the nearby tickets you scanned. What is your
     * ticket scanning error rate?
     * <p>
     * --- Part Two ---
     * <p>
     * Now that you've identified which tickets contain invalid values, discard
     * those tickets entirely. Use the remaining valid tickets to determine which
     * field is which.
     * <p>
     * Using the valid ranges for each field, determine what order the fields
     * appear on the tickets. The order is consistent between all tickets: if seat
     * is the third field, it is the third field on every ticket, including your
     * ticket.
     * <p>
     * For example, suppose you have the following notes:
     * <p>
     * class: 0-1 or 4-19
     * row: 0-5 or 8-19
     * seat: 0-13 or 16-19
     * <p>
     * your ticket:
     * 11,12,13
     * <p>
     * nearby tickets:
     * 3,9,18
     * 15,1,5
     * 5,14,9
     * <p>
     * Based on the nearby tickets in the above example, the first position must
     * be row, the second position must be class, and the third position must be
     * seat; you can conclude that in your ticket, class is 12, row is 11, and
     * seat is 13.
     * <p>
     * Once you work out which field is which, look for the six fields on your
     * ticket that start with the word departure. What do you get if you multiply
     * those six values together?
     */
    static Map<String, Integer> ticketTranslation(List<String> notes) {
        Triple<Map<String, IntPredicate>, int[], List<int[]>> triple = readNote(notes);
        Map<String, IntPredicate> rules = triple.getLeft();
        int[] yourTicket = triple.getMiddle();
        List<int[]> nearbyTickets = triple.getRight();

        LOGGER.info("Rules {}", rules);
        LOGGER.info("Your ticket {}", yourTicket);
        LOGGER.info("Nearby tickets {}", nearbyTickets);

        List<int[]> validTickets = new ArrayList<>();
        for (int[] ticket : nearbyTickets) {
            if (Arrays.stream(ticket)
                    .allMatch(t -> rules.values().stream().anyMatch(r -> r.test(t)))) {
                validTickets.add(ticket);
            }
        }

        LOGGER.info("Valid tickets {}", validTickets);

        Map<String, IntSet> ticketFields = new HashMap<>();

        for (Map.Entry<String, IntPredicate> entry : rules.entrySet()) {
            String key = entry.getKey();
            IntPredicate value = entry.getValue();

            for (int i = 0; i < yourTicket.length; i++) {
                int field = yourTicket[i];
                if (!value.test(field)) {
                    continue;
                }
                int finalI = i;
                int[] nearbyTicket = validTickets.stream().mapToInt(t -> t[finalI]).toArray();
                if (Arrays.stream(nearbyTicket).allMatch(value)) {
                    ticketFields.computeIfAbsent(key, _ -> new IntOpenHashSet()).add(i);
                }
            }
        }

        Map<String, Integer> result = new HashMap<>();

        LOGGER.info("ticketFields: {}", ticketFields);
        while (ticketFields.values().stream().anyMatch(s -> !s.isEmpty())) {
            Map.Entry<String, IntSet> first = ticketFields.entrySet().stream().filter(e -> e.getValue().size() == 1).findFirst().orElseThrow();
            int index = first.getValue().iterator().nextInt();
            result.put(first.getKey(), yourTicket[index]);
            ticketFields.values().forEach(s -> s.remove(index));
        }

        LOGGER.info("Result: {}", result);

        return result;
    }

    private record SimpleRule(int lowerBound, int upperBound) implements IntPredicate {
        @Override
        public String toString() {
            return "SimpleRule{" +
                   "lowerBound=" + lowerBound +
                   ", upperBound=" + upperBound +
                   '}';
        }

        @Override
        public boolean test(int value) {
            return lowerBound <= value && value <= upperBound;
        }
    }
}
