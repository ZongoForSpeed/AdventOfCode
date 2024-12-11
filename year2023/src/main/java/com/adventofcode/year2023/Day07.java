package com.adventofcode.year2023;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day07 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern HAND_PATTERN = Pattern.compile("(\\w+) (\\d+)");

    private Day07() {
        // No-Op
    }

    /**
     * --- Day 7: Camel Cards ---
     * <p>
     * Your all-expenses-paid trip turns out to be a one-way, five-minute ride in
     * an airship. (At least it's a cool airship!) It drops you off at the edge of
     * a vast desert and descends back to Island Island.
     * <p>
     * "Did you bring the parts?"
     * <p>
     * You turn around to see an Elf completely covered in white clothing, wearing
     * goggles, and riding a large camel.
     * <p>
     * "Did you bring the parts?" she asks again, louder this time. You aren't
     * sure what parts she's looking for; you're here to figure out why the sand
     * stopped.
     * <p>
     * "The parts! For the sand, yes! Come with me; I will show you." She beckons
     * you onto the camel.
     * <p>
     * After riding a bit across the sands of Desert Island, you can see what look
     * like very large rocks covering half of the horizon. The Elf explains that
     * the rocks are all along the part of Desert Island that is directly above
     * Island Island, making it hard to even get there. Normally, they use big
     * machines to move the rocks and filter the sand, but the machines have
     * broken down because Desert Island recently stopped receiving the parts they
     * need to fix the machines.
     * <p>
     * You've already assumed it'll be your job to figure out why the parts
     * stopped when she asks if you can help. You agree automatically.
     * <p>
     * Because the journey will take a few days, she offers to teach you the game
     * of Camel Cards. Camel Cards is sort of similar to poker except it's
     * designed to be easier to play while riding a camel.
     * <p>
     * In Camel Cards, you get a list of hands, and your goal is to order them
     * based on the strength of each hand. A hand consists of five cards labeled
     * one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2. The relative strength of
     * each card follows this order, where A is the highest and 2 is the lowest.
     * <p>
     * Every hand is exactly one type. From strongest to weakest, they are:
     * <p>
     * - Five of a kind, where all five cards have the same label: AAAAA
     * - Four of a kind, where four cards have the same label and one card has
     * a different label: AA8AA
     * - Full house, where three cards have the same label, and the remaining
     * two cards share a different label: 23332
     * - Three of a kind, where three cards have the same label, and the
     * remaining two cards are each different from any other card in the
     * hand: TTT98
     * - Two pair, where two cards share one label, two other cards share a
     * second label, and the remaining card has a third label: 23432
     * - One pair, where two cards share one label, and the other three cards
     * have a different label from the pair and each other: A23A4
     * - High card, where all cards' labels are distinct: 23456
     * <p>
     * Hands are primarily ordered based on type; for example, every full house is
     * stronger than any three of a kind.
     * <p>
     * If two hands have the same type, a second ordering rule takes effect. Start
     * by comparing the first card in each hand. If these cards are different, the
     * hand with the stronger first card is considered stronger. If the first card
     * in each hand have the same label, however, then move on to considering the
     * second card in each hand. If they differ, the hand with the higher second
     * card wins; otherwise, continue with the third card in each hand, then the
     * fourth, then the fifth.
     * <p>
     * So, 33332 and 2AAAA are both four of a kind hands, but 33332 is stronger
     * because its first card is stronger. Similarly, 77888 and 77788 are both a
     * full house, but 77888 is stronger because its third card is stronger (and
     * both hands have the same first and second card).
     * <p>
     * To play Camel Cards, you are given a list of hands and their corresponding
     * bid (your puzzle input). For example:
     * <p>
     * 32T3K 765
     * T55J5 684
     * KK677 28
     * KTJJT 220
     * QQQJA 483
     * <p>
     * This example shows five hands; each hand is followed by its bid amount.
     * Each hand wins an amount equal to its bid multiplied by its rank, where the
     * weakest hand gets rank 1, the second-weakest hand gets rank 2, and so on up
     * to the strongest hand. Because there are five hands in this example, the
     * strongest hand will have rank 5 and its bid will be multiplied by 5.
     * <p>
     * So, the first step is to put the hands in order of strength:
     * <p>
     * - 32T3K is the only one pair and the other hands are all a stronger
     * type, so it gets rank 1.
     * - KK677 and KTJJT are both two pair. Their first cards both have the
     * same label, but the second card of KK677 is stronger (K vs T), so
     * KTJJT gets rank 2 and KK677 gets rank 3.
     * - T55J5 and QQQJA are both three of a kind. QQQJA has a stronger first
     * card, so it gets rank 5 and T55J5 gets rank 4.
     * <p>
     * Now, you can determine the total winnings of this set of hands by adding up
     * the result of multiplying each hand's bid with its rank (765 * 1 + 220 * 2
     * + 28 * 3 + 684 * 4 + 483 * 5). So the total winnings in this example are
     * 6440.
     * <p>
     * Find the rank of every hand in your set. What are the total winnings?
     */
    public static final class PartOne {
        private static final Map<Character, Integer> VALUES = Map.ofEntries(
                Map.entry('A', 14),
                Map.entry('K', 13),
                Map.entry('Q', 12),
                Map.entry('J', 11),
                Map.entry('T', 10),
                Map.entry('9', 9),
                Map.entry('8', 8),
                Map.entry('7', 7),
                Map.entry('6', 6),
                Map.entry('5', 5),
                Map.entry('4', 4),
                Map.entry('3', 3),
                Map.entry('2', 2)
        );

        private PartOne() {
            // No-Op
        }

        public static long computeWinnings(Scanner scanner) {
            List<Hand> hands = readHands(scanner, VALUES, s -> type((String) s));

            hands.sort(Hand::compareTo);

            long[] bids = hands.stream().mapToLong(Hand::bid).toArray();

            long winnings = 0;
            for (int i = 0; i < bids.length; i++) {
                winnings += (i + 1) * bids[i];
            }
            return winnings;
        }

        public static Type type(String cards) {
            Char2IntMap map = new Char2IntOpenHashMap();

            for (int i = 0; i < cards.length(); i++) {
                map.merge(cards.charAt(i), 1, Integer::sum);
            }

            return computeTypeSimple(map);
        }
    }

    private static List<Hand> readHands(Scanner scanner, Map<Character, Integer> values, Function<String, Type> function) {
        List<Hand> hands = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = HAND_PATTERN.matcher(line);
            if (matcher.find()) {
                String cards = matcher.group(1);
                long bid = Long.parseLong(matcher.group(2));

                Hand hand = Hand.of(cards, bid, values, function);

                LOGGER.info("hand = {}", hand);
                hands.add(hand);
            } else {
                throw new IllegalStateException("Cannot parse hand: " + line);
            }
        }
        return hands;
    }

    /**
     * --- Part Two ---
     * <p>
     * To make things a little more interesting, the Elf introduces one additional
     * rule. Now, J cards are jokers - wildcards that can act like whatever card
     * would make the hand the strongest type possible.
     * <p>
     * To balance this, J cards are now the weakest individual cards, weaker even
     * than 2. The other cards stay in the same order: A, K, Q, T, 9, 8, 7, 6, 5,
     * 4, 3, 2, J.
     * <p>
     * J cards can pretend to be whatever card is best for the purpose of
     * determining hand type; for example, QJJQ2 is now considered four of a kind.
     * However, for the purpose of breaking ties between two hands of the same
     * type, J is always treated as J, not the card it's pretending to be: JKKK2
     * is weaker than QQQQ2 because J is weaker than Q.
     * <p>
     * Now, the above example goes very differently:
     * <p>
     * 32T3K 765
     * T55J5 684
     * KK677 28
     * KTJJT 220
     * QQQJA 483
     * <p>
     * - 32T3K is still the only one pair; it doesn't contain any jokers, so
     * its strength doesn't increase.
     * - KK677 is now the only two pair, making it the second-weakest hand.
     * - T55J5, KTJJT, and QQQJA are now all four of a kind! T55J5 gets rank 3,
     * QQQJA gets rank 4, and KTJJT gets rank 5.
     * <p>
     * With the new joker rule, the total winnings in this example are 5905.
     * <p>
     * Using the new joker rule, find the rank of every hand in your set. What are
     * the new total winnings?
     */
    public static final class PartTwo {
        private static final Map<Character, Integer> VALUES = Map.ofEntries(
                Map.entry('A', 14),
                Map.entry('K', 13),
                Map.entry('Q', 12),
                Map.entry('T', 10),
                Map.entry('9', 9),
                Map.entry('8', 8),
                Map.entry('7', 7),
                Map.entry('6', 6),
                Map.entry('5', 5),
                Map.entry('4', 4),
                Map.entry('3', 3),
                Map.entry('2', 2),
                Map.entry('J', 1)
        );

        private PartTwo() {
            // No-Op
        }

        public static long computeWinnings(Scanner scanner) {
            List<Hand> hands = readHands(scanner, VALUES, s -> type((String) s));

            hands.sort(Hand::compareTo);

            long[] bids = hands.stream().mapToLong(Hand::bid).toArray();

            long winnings = 0;
            for (int i = 0; i < bids.length; i++) {
                winnings += (i + 1) * bids[i];
            }
            return winnings;
        }

        public static Type type(String cards) {
            Char2IntMap map = new Char2IntOpenHashMap();

            for (int i = 0; i < cards.length(); i++) {
                map.merge(cards.charAt(i), 1, Integer::sum);
            }

            int joker = map.getOrDefault('J', 0);
            if (joker == 0) {
                return computeTypeSimple(map);
            }

            return computeTypeJoker(map, joker);
        }


        private static Type computeTypeJoker(Char2IntMap map, int joker) {
            int size = map.size();
            switch (size) {
                case 1, 2 -> {
                    return Type.FIVE_OF_A_KIND;
                }
                case 3 -> {
                    if (map.containsValue(3)) {
                        return Type.FOUR_OF_A_KIND;
                    } else if (joker == 2) {
                        return Type.FOUR_OF_A_KIND;
                    } else if (joker == 1) {
                        return Type.FULL_HOUSE;
                    } else {
                        return Type.TWO_PAIR;
                    }
                }
                case 4 -> {
                    return Type.THREE_OF_A_KIND;
                }
                case 5 -> {
                    return Type.ONE_PAIR;
                }
                default -> throw new IllegalStateException();
            }
        }
    }


    private static Type computeTypeSimple(Char2IntMap map) {
        int size = map.size();
        switch (size) {
            case 1 -> {
                return Type.FIVE_OF_A_KIND;
            }
            case 2 -> {
                if (map.containsValue(4)) {
                    return Type.FOUR_OF_A_KIND;
                } else {
                    return Type.FULL_HOUSE;
                }
            }
            case 3 -> {
                if (map.containsValue(3)) {
                    return Type.THREE_OF_A_KIND;
                } else {
                    return Type.TWO_PAIR;
                }
            }
            case 4 -> {
                return Type.ONE_PAIR;
            }
            case 5 -> {
                return Type.HIGH_CARD;
            }
            default -> throw new IllegalStateException();
        }
    }


    public enum Type {
        FIVE_OF_A_KIND(6),
        FOUR_OF_A_KIND(5),
        FULL_HOUSE(4),
        THREE_OF_A_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGH_CARD(0);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    record Hand(String cards, long bid, Type type, IntList values) implements Comparable<Hand> {

        public static Hand of(String cards, long bid, Map<Character, Integer> cardValues, Function<String, Type> function) {
            int[] values = new int[cards.length()];
            for (int i = 0, charArrayLength = cards.length(); i < charArrayLength; i++) {
                values[i] = cardValues.get(cards.charAt(i));
            }
            return new Hand(cards, bid, function.apply(cards), IntList.of(values));
        }


        @Override
        public int compareTo(Hand o) {
            int compare = Integer.compare(type.value(), o.type.value());
            if (compare != 0) {
                return compare;
            }

            for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
                compare = Integer.compare(values.getInt(i), o.values.getInt(i));
                if (compare != 0) {
                    return compare;
                }
            }

            return 0;
        }
    }
}
