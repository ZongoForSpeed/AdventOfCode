package com.adventofcode.year2019;

import com.adventofcode.common.maths.Arithmetic;
import com.adventofcode.common.matrix.Matrix2D;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public final class Day22 {
    public static final String DEAL_WITH_INCREMENT = "deal with increment ";
    public static final String DEAL_INTO_NEW_STACK = "deal into new stack";
    public static final String CUT_N_CARDS = "cut ";

    private Day22() {
        // No-Op
    }

    /**
     * --- Day 22: Slam Shuffle ---
     * There isn't much to do while you wait for the droids to repair your ship. At least you're drifting in the right
     * direction. You decide to practice a new card shuffle you've been working on.
     *
     * Digging through the ship's storage, you find a deck of space cards! Just like any deck of space cards, there are
     * 10007 cards in the deck numbered 0 through 10006. The deck must be new - they're still in factory order, with 0
     * on the top, then 1, then 2, and so on, all the way through to 10006 on the bottom.
     *
     * You've been practicing three different techniques that you use while shuffling. Suppose you have a deck of only
     * 10 cards (numbered 0 through 9):
     *
     * To deal into new stack, create a new stack of cards by dealing the top card of the deck onto the top of the new
     * stack repeatedly until you run out of cards:
     *
     * Top          Bottom
     * 0 1 2 3 4 5 6 7 8 9   Your deck
     * New stack
     *
     * 1 2 3 4 5 6 7 8 9   Your deck
     * 0   New stack
     *
     * 2 3 4 5 6 7 8 9   Your deck
     * 1 0   New stack
     *
     * 3 4 5 6 7 8 9   Your deck
     * 2 1 0   New stack
     *
     * Several steps later...
     *
     * 9   Your deck
     * 8 7 6 5 4 3 2 1 0   New stack
     *
     * Your deck
     * 9 8 7 6 5 4 3 2 1 0   New stack
     * Finally, pick up the new stack you've just created and use it as the deck for the next technique.
     *
     * To cut N cards, take the top N cards off the top of the deck and move them as a single unit to the bottom of the
     * deck, retaining their order. For example, to cut 3:
     *
     * Top          Bottom
     * 0 1 2 3 4 5 6 7 8 9   Your deck
     *
     * 3 4 5 6 7 8 9   Your deck
     * 0 1 2                 Cut cards
     *
     * 3 4 5 6 7 8 9         Your deck
     * 0 1 2   Cut cards
     *
     * 3 4 5 6 7 8 9 0 1 2   Your deck
     * You've also been getting pretty good at a version of this technique where N is negative! In that case, cut (the
     * absolute value of) N cards from the bottom of the deck onto the top. For example, to cut -4:
     *
     * Top          Bottom
     * 0 1 2 3 4 5 6 7 8 9   Your deck
     *
     * 0 1 2 3 4 5           Your deck
     * 6 7 8 9   Cut cards
     *
     * 0 1 2 3 4 5   Your deck
     * 6 7 8 9               Cut cards
     *
     * 6 7 8 9 0 1 2 3 4 5   Your deck
     * To deal with increment N, start by clearing enough space on your table to lay out all of the cards individually
     * in a long line. Deal the top card into the leftmost position. Then, move N positions to the right and deal the
     * next card there. If you would move into a position past the end of the space on your table, wrap around and keep
     * counting from the leftmost card again. Continue this process until you run out of cards.
     *
     * For example, to deal with increment 3:
     *
     *
     * 0 1 2 3 4 5 6 7 8 9   Your deck
     * . . . . . . . . . .   Space on table
     * ^                     Current position
     *
     * Deal the top card to the current position:
     *
     * 1 2 3 4 5 6 7 8 9   Your deck
     * 0 . . . . . . . . .   Space on table
     * ^                     Current position
     *
     * Move the current position right 3:
     *
     * 1 2 3 4 5 6 7 8 9   Your deck
     * 0 . . . . . . . . .   Space on table
     * ^               Current position
     *
     * Deal the top card:
     *
     * 2 3 4 5 6 7 8 9   Your deck
     * 0 . . 1 . . . . . .   Space on table
     * ^               Current position
     *
     * Move right 3 and deal:
     *
     * 3 4 5 6 7 8 9   Your deck
     * 0 . . 1 . . 2 . . .   Space on table
     * ^         Current position
     *
     * Move right 3 and deal:
     *
     * 4 5 6 7 8 9   Your deck
     * 0 . . 1 . . 2 . . 3   Space on table
     * ^   Current position
     *
     * Move right 3, wrapping around, and deal:
     *
     * 5 6 7 8 9   Your deck
     * 0 . 4 1 . . 2 . . 3   Space on table
     * ^                 Current position
     *
     * And so on:
     *
     * 0 7 4 1 8 5 2 9 6 3   Space on table
     * Positions on the table which already contain cards are still counted; they're not skipped. Of course, this
     * technique is carefully designed so it will never put two cards in the same position or leave a position empty.
     *
     * Finally, collect the cards on the table so that the leftmost card ends up at the top of your deck, the card to
     * its right ends up just below the top card, and so on, until the rightmost card ends up at the bottom of the deck.
     *
     * The complete shuffle process (your puzzle input) consists of applying many of these techniques. Here are some
     * examples that combine techniques; they all start with a factory order deck of 10 cards:
     *
     * deal with increment 7
     * deal into new stack
     * deal into new stack
     * Result: 0 3 6 9 2 5 8 1 4 7
     * cut 6
     * deal with increment 7
     * deal into new stack
     * Result: 3 0 7 4 1 8 5 2 9 6
     * deal with increment 7
     * deal with increment 9
     * cut -2
     * Result: 6 3 0 7 4 1 8 5 2 9
     * deal into new stack
     * cut -2
     * deal with increment 7
     * cut 8
     * cut -4
     * deal with increment 7
     * cut 3
     * deal with increment 9
     * deal with increment 3
     * cut -1
     * Result: 9 2 5 8 1 4 7 0 3 6
     * Positions within the deck count from 0 at the top, then 1 for the card immediately below the top card, and so on to the bottom. (That is, cards start in the position matching their number.)
     *
     * After shuffling your factory order deck of 10007 cards, what is the position of card 2019?
     */
    static IntList dealIntoNewStack(IntList cards) {
        IntList result = new IntArrayList(cards);
        Collections.reverse(result);
        return result;
    }

    static IntList cutCards(IntList cards, int n) {
        IntList result = new IntArrayList();
        if (n > 0) {
            result.addAll(cards.subList(n, cards.size()));
            result.addAll(cards.subList(0, n));
        } else {
            result.addAll(cards.subList(cards.size() + n, cards.size()));
            result.addAll(cards.subList(0, cards.size() + n));
        }

        return result;
    }

    static IntList dealWithIncrement(IntList cards, int n) {
        int[] result = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            result[(i * n) % cards.size()] = cards.getInt(i);
        }

        return new IntArrayList(result);
    }

    static IntList slamShuffle(IntList cards, List<String> commands) {
        for (String command : commands) {
            if (command.startsWith(DEAL_WITH_INCREMENT)) {
                int increment = Integer.parseInt(command.substring(DEAL_WITH_INCREMENT.length()));
                cards = dealWithIncrement(cards, increment);
            } else if (command.startsWith(DEAL_INTO_NEW_STACK)) {
                cards = dealIntoNewStack(cards);
            } else if (command.startsWith(CUT_N_CARDS)) {
                int argument = Integer.parseInt(command.substring(CUT_N_CARDS.length()));
                cards = cutCards(cards, argument);
            } else {
                throw new IllegalStateException("Unknown command '" + command + "'");
            }
        }

        return cards;
    }

    static long slamShuffle(long position, long modulus, List<String> commands) {
        for (String command : commands) {
            if (command.startsWith(DEAL_WITH_INCREMENT)) {
                int increment = Integer.parseInt(command.substring(DEAL_WITH_INCREMENT.length()));
                position = multiplyMod(position, increment, modulus);
                // position = (position + modulus - increment) % modulus;
            } else if (command.startsWith(DEAL_INTO_NEW_STACK)) {
                position = modulus - 1 - position;
            } else if (command.startsWith(CUT_N_CARDS)) {
                int argument = Integer.parseInt(command.substring(CUT_N_CARDS.length()));
                position = (position + modulus - argument) % modulus;
            } else {
                throw new IllegalStateException("Unknown command '" + command + "'");
            }
        }

        return position;
    }

    private static long multiplyMod(long a, long b, long modulus) {
        return multiplyMod(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(modulus));
    }

    private static long multiplyMod(BigInteger a, BigInteger b, BigInteger modulus) {
        return a.multiply(b).mod(modulus).longValue();
    }

    static long inverseSlamShuffle(List<String> commands, long position, long modulus) {
        Collections.reverse(commands);
        for (String command : commands) {
            if (command.startsWith(DEAL_WITH_INCREMENT)) {
                int increment = Integer.parseInt(command.substring(DEAL_WITH_INCREMENT.length()));
                position = multiplyMod(position, Arithmetic.inverseModulaire(increment, modulus), modulus);
                // position = (position + modulus - increment) % modulus;
            } else if (command.startsWith(DEAL_INTO_NEW_STACK)) {
                position = modulus - 1 - position;
            } else if (command.startsWith(CUT_N_CARDS)) {
                int argument = Integer.parseInt(command.substring(CUT_N_CARDS.length()));
                position = (position + modulus + argument) % modulus;
            } else {
                throw new IllegalStateException("Unknown command '" + command + "'");
            }
        }

        return position;
    }

    /**
     * --- Part Two ---
     * After a while, you realize your shuffling skill won't improve much more with merely a single deck of cards. You
     * ask every 3D printer on the ship to make you some more cards while you check on the ship repairs. While reviewing
     * the work the droids have finished so far, you think you see Halley's Comet fly past!
     *
     * When you get back, you discover that the 3D printers have combined their power to create for you a single, giant,
     * brand new, factory order deck of 119315717514047 space cards.
     *
     * Finally, a deck of cards worthy of shuffling!
     *
     * You decide to apply your complete shuffle process (your puzzle input) to the deck 101741582076661 times in a row.
     *
     * You'll need to be careful, though - one wrong move with this many cards and you might overflow your entire ship!
     *
     * After shuffling your new, giant, factory order deck that many times, what number is on the card that ends up in
     * position 2020?
     */
    static long inverseSlamShuffle(List<String> commands, long position, long modulus, long exponent) {
        Matrix2D m = new Matrix2D(1, 0, 0, 1);

        for (String command : commands) {
            if (command.startsWith(DEAL_WITH_INCREMENT)) {
                long argument = Integer.parseInt(command.substring(DEAL_WITH_INCREMENT.length()));
                argument = Arithmetic.inverseModulaire(argument, modulus);
                m = m.multiply(new Matrix2D(argument, 0, 0, 1), modulus);
            } else if (command.startsWith(DEAL_INTO_NEW_STACK)) {
                m = m.multiply(new Matrix2D(-1, modulus - 1, 0, 1), modulus);
            } else if (command.startsWith(CUT_N_CARDS)) {
                int argument = Integer.parseInt(command.substring(CUT_N_CARDS.length()));
                m = m.multiply(new Matrix2D(1, argument, 0, 1), modulus);
            } else {
                throw new IllegalStateException("Unknown command '" + command + "'");
            }
        }

        m = Matrix2D.power(m, exponent, modulus);

        return (m.a11() * position + m.a12()) % modulus;
    }
}
